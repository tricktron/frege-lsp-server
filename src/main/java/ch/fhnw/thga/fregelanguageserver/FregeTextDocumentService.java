package ch.fhnw.thga.fregelanguageserver;

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
import frege.run8.Func.U;
import frege.runtime.Phantom.RealWorld;

public class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
    // TODO: Change type to TGlobal only
    private U<RealWorld, TGlobal> global;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer    = server;
		currentOpenFileContents = "";
        // TODO: Get with performUnsafe
        global                  = CompileGlobal.fromOptions(server.getCompileOptions());
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
        // Todo: get with performUnsafe
        global                   = CompileExecutor.compile
            (
                Thunk.lazy(currentOpenFileContents),
                global
            );
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
