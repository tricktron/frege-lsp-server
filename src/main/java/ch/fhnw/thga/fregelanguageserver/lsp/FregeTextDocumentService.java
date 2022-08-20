package ch.fhnw.thga.fregelanguageserver.lsp;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticService;
import ch.fhnw.thga.fregelanguageserver.hover.HoverService;

class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
    private final CompileService compileService;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer = server;
        compileService       = new CompileService();
	}

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        return HoverService.hover
        (
            params,
            compileService.getGlobal(URI.create(params.getTextDocument().getUri()))
        );
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
        URI uri = URI.create(params.getTextDocument().getUri());
        compileService.compileAndUpdateGlobals
        (
            uri, 
            simpleLanguageServer.getProjectService().getProjectGlobal()
        );
        DiagnosticService.publishCompilerDiagnostics
        (
            simpleLanguageServer.getClient(),
            compileService.getGlobal(uri),
            uri.toString()
        );
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params)
    {
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
    {
        URI uri = URI.create(params.getTextDocument().getUri());
        compileService.compileAndUpdateGlobals
        (
            uri, 
            simpleLanguageServer.getProjectService().getProjectGlobal()
        );
        DiagnosticService.publishCompilerDiagnostics
        (
            simpleLanguageServer.getClient(),
            compileService.getGlobal(uri),
            uri.toString()
        );
    }
}
