package ch.fhnw.thga;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.services.TextDocumentService;

import frege.compiler.types.Global.TGlobal;
import frege.prelude.PreludeBase.TList;
import frege.prelude.PreludeBase.TTuple2;
import frege.repl.FregeRepl.TReplEnv;
import frege.repl.FregeRepl.TReplResult;
import frege.run8.Lazy;
import frege.run8.Thunk;

public class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
    private List<String> currentOpenFileLines;
	private Lazy<TReplEnv> replEnv;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer    = server;
		currentOpenFileContents = "";
        currentOpenFileLines    = new ArrayList<>();
		replEnv = performUnsafe(TypeSignature.initialReplEnv.call());
	}

	@Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
		return CompletableFutures.computeAsync(cancel -> 
        {
			if (cancel.isCanceled())
            {
			    return null;
			}
			return FregeHoverService.getFunctionTypeSignatureOnLine(
                currentOpenFileContents,
                params.getPosition().getLine(),
                replEnv);
		});
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
		currentOpenFileContents = params.getTextDocument().getText();
        currentOpenFileLines    = currentOpenFileContents
            .lines()
            .collect(Collectors.toList());
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(
                    Thunk.lazy(currentOpenFileContents),
                    replEnv)
                ).call();
		replEnv = resEnvTuple.mem2;
        try {
            TGlobal global = performUnsafe(
                CompilerExploration.compileFregeProject(TList.DCons.mk(
                    Thunk.lazy(new URI(params.getTextDocument().getUri()).getPath()), 
                    TList.DList.mk()))
            ).call();
            FregeDiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.client,
            global,
            params.getTextDocument().getUri()
        );
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		// FregeDiagnosticService.publishCompilerDiagnostics(
        //     simpleLanguageServer.client,
        //     resEnvTuple.mem1.call(),
        //     params.getTextDocument().getUri()
        // );
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params)
    {
		List<TextDocumentContentChangeEvent> changes = params.getContentChanges();
		currentOpenFileContents = changes.get(changes.size() - 1).getText();
        currentOpenFileLines    = currentOpenFileContents
            .lines()
            .collect(Collectors.toList());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params)
    {
		currentOpenFileContents = "";
        currentOpenFileLines.clear();
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params)
    {
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(
                    Thunk.lazy(currentOpenFileContents),
                    replEnv)
                ).call();
		replEnv = resEnvTuple.mem2;
		FregeDiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.client,
            resEnvTuple.mem1.call(),
            params.getTextDocument().getUri()
        );
	}
}
