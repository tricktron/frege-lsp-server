package ch.fhnw.thga.fregelanguageserver.lsp;

import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.fregelanguageserver.project.ProjectService;


class FregeLanguageServer implements LanguageServer, LanguageClientAware 
{
	private final FregeTextDocumentService textService;
	private final WorkspaceService workspaceService;
	private LanguageClient client;
    private ProjectService projectService;

	public FregeLanguageServer()
    {
		textService      = new FregeTextDocumentService(this);
		workspaceService = new FregeWorkspaceService();
	}

    public LanguageClient getClient()
    {
        return client;
    }

    public ProjectService getProjectService()
    {
        return projectService;
    }

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params)
    {
        projectService             = ProjectService.fromRootPath
        (
            Path.of(URI.create(params.getWorkspaceFolders().get(0).getUri()))
        );
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Incremental);
		res.getCapabilities().setHoverProvider(true);
		return CompletableFuture.supplyAsync(() -> res);
	}

	@Override
	public CompletableFuture<Object> shutdown() 
    {
        CompileService.shutdownCompilerExecutorService();
		return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
	}

	@Override
	public void exit() 
    {
		System.exit(0);
	}

	@Override
	public TextDocumentService getTextDocumentService() 
    {
		return this.textService;
	}

	@Override
	public WorkspaceService getWorkspaceService()
    {
		return this.workspaceService;
	}

	@Override
	public void connect(LanguageClient client)
    {
		this.client = client;
	}
}