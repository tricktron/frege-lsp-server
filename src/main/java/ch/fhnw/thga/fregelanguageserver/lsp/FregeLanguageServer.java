package ch.fhnw.thga.fregelanguageserver.lsp;

import static java.util.Collections.singletonList;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeWatchedFilesRegistrationOptions;
import org.eclipse.lsp4j.FileSystemWatcher;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.Registration;
import org.eclipse.lsp4j.RegistrationParams;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.WatchKind;
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
    private final CompileService compileService;
	private LanguageClient client;
    private ProjectService projectService;

	public FregeLanguageServer()
    {
        compileService   = new CompileService();
		textService      = new FregeTextDocumentService(this, compileService);
		workspaceService = new FregeWorkspaceService(this, compileService);
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
    public void initialized(InitializedParams params)
    {
        FileSystemWatcher fregeWatcher = new FileSystemWatcher
        (
            "/**/*.fr",
            WatchKind.Create + WatchKind.Change + WatchKind.Delete
        );
        // TODO: use TextDocumentRegistrationOptions when going forward with this change
        DidChangeWatchedFilesRegistrationOptions options = 
            new DidChangeWatchedFilesRegistrationOptions(singletonList(fregeWatcher));
        Registration registration = new Registration
        (
            UUID.randomUUID().toString(),
            "workspace/didChangeWatchedFiles",
            options
        );
        client.registerCapability(new RegistrationParams(singletonList(registration)));
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