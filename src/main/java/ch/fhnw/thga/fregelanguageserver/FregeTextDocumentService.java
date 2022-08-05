package ch.fhnw.thga.fregelanguageserver;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.fregelanguageserver.compile.CompileGlobal;
import ch.fhnw.thga.fregelanguageserver.compile.CompileMakeMode;
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
    private HashMap<URI, TGlobal> uriGlobals;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer = server;
        uriGlobals           = new HashMap<>();
	}

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        TGlobal global = uriGlobals.get(URI.create(params.getTextDocument().getUri()));
        return HoverService.hover(params, global);
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
        List<TGlobal> globals = performUnsafe
        (
            CompileMakeMode.compileMakeLSP
            (
                Thunk.lazy(URI.create(params.getTextDocument().getUri()).getPath()),
                STANDARD_GLOBAL
            )
        ).call();
        globals.forEach(global -> 
        {
            URI uri = Path.of(global.mem$options.mem$source).normalize().toUri();
            uriGlobals.put(uri, global);
            DiagnosticService.publishCompilerDiagnostics
            (
                simpleLanguageServer.getClient(),
                global,
                uri.toString()
            );
        });
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
        List<TGlobal> globals = performUnsafe
        (
            CompileMakeMode.compileMakeLSP
            (
                Thunk.lazy(URI.create(params.getTextDocument().getUri()).getPath()),
                STANDARD_GLOBAL
            )
        ).call();
        globals.forEach(global -> 
        {
            URI uri = Path.of(global.mem$options.mem$source).normalize().toUri();
            uriGlobals.put(uri, global);
            DiagnosticService.publishCompilerDiagnostics
            (
                simpleLanguageServer.getClient(),
                global,
                uri.toString()
            );
        });
    }
}
