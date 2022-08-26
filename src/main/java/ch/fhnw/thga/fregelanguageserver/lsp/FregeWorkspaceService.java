package ch.fhnw.thga.fregelanguageserver.lsp;

import java.net.URI;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticService;

class FregeWorkspaceService implements WorkspaceService
{
	private final FregeLanguageServer fregeServer;
    private final CompileService compileService;

    public FregeWorkspaceService(FregeLanguageServer fregeServer, CompileService compileService)
    {
        this.fregeServer = fregeServer;
        this.compileService = compileService;
    }

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params)
    {
        // TODO: extract function to functional consumer, give it good name and call it in forEach
        params.getChanges().forEach
        (
            change -> 
            {
                URI uri = URI.create(change.getUri());
                compileService.compileAndUpdateGlobals
                (
                    uri, 
                    fregeServer.getProjectService().getProjectGlobal()
                );
                DiagnosticService.publishCompilerDiagnostics
                (
                    fregeServer.getClient(),
                    compileService.getGlobal(uri),
                    uri.toString()
                );
            }
        );
	}
}