package ch.fhnw.thga.fregelanguageserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;


public class FregeLanguageServer implements LanguageServer, LanguageClientAware 
{
	private final FregeTextDocumentService textService;
	private final WorkspaceService workspaceService;
	private LanguageClient client;
    private TGlobal projectGlobal;

	public FregeLanguageServer()
    {
		textService      = new FregeTextDocumentService(this);
		workspaceService = new FregeWorkspaceService();
	}

    public LanguageClient getClient()
    {
        return client;
    }

    public TGlobal getProjectGlobal()
    {
        return projectGlobal;
    }

    private TOptions createProjectOptions()
    {
        return CompileService.STANDARD_COMPILE_OPTIONS;
    }

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params)
    {
        TOptions projectOptions = createProjectOptions();
        try 
        {
            Files.createDirectories(Paths.get(projectOptions.mem$dir).normalize());
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        projectGlobal = CompileService.createCompileGlobal(projectOptions);
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
		res.getCapabilities().setHoverProvider(true);
		return CompletableFuture.supplyAsync(() -> res);
	}

	@Override
	public CompletableFuture<Object> shutdown() 
    {
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