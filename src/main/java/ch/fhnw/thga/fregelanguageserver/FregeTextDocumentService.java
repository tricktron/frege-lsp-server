package ch.fhnw.thga.fregelanguageserver;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.fregelanguageserver.compile.CompileExecutor;
import ch.fhnw.thga.fregelanguageserver.compile.CompileGlobal;
import ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticService;
import ch.fhnw.thga.fregelanguageserver.hover.HoverService;
import frege.compiler.types.Global.TGlobal;
import frege.run8.Thunk;

public class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
    public static final TGlobal STANDARD_GLOBAL = performUnsafe
        (CompileGlobal.standardCompileGlobal.call()).call();
	private final FregeLanguageServer simpleLanguageServer;
    private HashMap<String, TGlobal> uriGlobals;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer    = server;
        uriGlobals              = new HashMap<>();
	}

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        TGlobal global = uriGlobals.get(params.getTextDocument().getUri());
        return global == null ? null : HoverService.hover(params, global);
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
        String uri             = params.getTextDocument().getUri();
        TGlobal compiledGlobal = performUnsafe
        (
            CompileExecutor.compile
            (
                Thunk.lazy(params.getTextDocument().getText()),
                STANDARD_GLOBAL
            )
        ).call();
        uriGlobals.put(uri, compiledGlobal);
		DiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.getClient(),
            compiledGlobal,
            params.getTextDocument().getUri()
        );
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params)
    {
		List<TextDocumentContentChangeEvent> changes 
                               = params.getContentChanges();
        String uri             = params.getTextDocument().getUri();
        TGlobal compiledGlobal = performUnsafe
        (
            CompileExecutor.compile
            (
                Thunk.lazy(changes.get(changes.size() - 1).getText()),
                STANDARD_GLOBAL
            )
        ).call();
        uriGlobals.put(uri, compiledGlobal);
        DiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.getClient(),
            compiledGlobal,
            uri
        );

        
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params)
    {
        DiagnosticService.cleanCompilerDiagnostics
        (
            simpleLanguageServer.getClient(), 
            params.getTextDocument().getUri()
        );
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params)
    {}
}
