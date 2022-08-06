package ch.fhnw.thga.fregelanguageserver;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
import frege.compiler.types.Global.TGlobal;

public class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
    private HashMap<URI, TGlobal> uriGlobals;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer = server;
        uriGlobals           = new HashMap<>();
	}

    final Consumer<TGlobal> updateUriGlobalsAndPublishDiagnostics = new Consumer<TGlobal>() 
    {
        @Override
        public final void accept(TGlobal global)
        {
            URI uri = Path.of(global.mem$options.mem$source).normalize().toUri();
            uriGlobals.put(uri, global);
            DiagnosticService.publishCompilerDiagnostics
            (
                simpleLanguageServer.getClient(),
                global,
                uri.toString()
            );
        }
    };

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        TGlobal global = uriGlobals.get(URI.create(params.getTextDocument().getUri()));
        return HoverService.hover(params, global);
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
        List<TGlobal> globals = CompileService.compileWithMakeMode
        (
            URI.create(params.getTextDocument().getUri()).getPath(), 
            simpleLanguageServer.getProjectGlobal()
        );
        globals.forEach(updateUriGlobalsAndPublishDiagnostics);
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
        List<TGlobal> globals = CompileService.compileWithMakeMode
        (
            URI.create(params.getTextDocument().getUri()).getPath(), 
            simpleLanguageServer.getProjectGlobal()
        );
        globals.forEach(updateUriGlobalsAndPublishDiagnostics);
    }
}
