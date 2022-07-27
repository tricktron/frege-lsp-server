package ch.fhnw.thga.fregelanguageserver;

import static frege.prelude.PreludeBase.TST.performUnsafe;

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
	private final FregeLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
    private TGlobal global;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer    = server;
		currentOpenFileContents = "";
        global                  = performUnsafe
        (CompileGlobal.standardCompileGlobal.call()).call();
	}

    public void setGlobal(TGlobal global)
    {
        this.global = global;
    }

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        return HoverService.hover(params, this.global);
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
		currentOpenFileContents  = params.getTextDocument().getText();
        global                   = performUnsafe
        (
            CompileExecutor.compile
            (
                Thunk.lazy(currentOpenFileContents),
                global
            )
        ).call();
		DiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.getClient(),
            global,
            params.getTextDocument().getUri()
        );
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params)
    {
		List<TextDocumentContentChangeEvent> changes 
                                = params.getContentChanges();
		currentOpenFileContents = changes.get(changes.size() - 1).getText();
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params)
    {
		currentOpenFileContents = "";
        DiagnosticService.cleanCompilerDiagnostics
        (
            simpleLanguageServer.getClient(), 
            params.getTextDocument().getUri()
        );
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params)
    {
		DiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.getClient(),
            this.global,
            params.getTextDocument().getUri()
        );
	}
}
