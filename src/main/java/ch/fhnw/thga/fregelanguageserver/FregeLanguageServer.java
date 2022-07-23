package ch.fhnw.thga.fregelanguageserver;

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

import ch.fhnw.thga.fregelanguageserver.compile.CompileOptions;
import frege.compiler.types.Global.TOptions;
import frege.run8.Lazy;

public class FregeLanguageServer implements LanguageServer, LanguageClientAware 
{
	private final TextDocumentService textService;
	private final WorkspaceService workspaceService;
	private LanguageClient client;
    private TOptions compileOptions;

	public FregeLanguageServer()
    {
		textService      = new FregeTextDocumentService(this);
		workspaceService = new FregeWorkspaceService();
        compileOptions   = CompileOptions.standardCompileOptions.call();
	}

    public LanguageClient getClient()
    {
        return client;
    }

    public TOptions getCompileOptions()
    {
        return compileOptions;
    }

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params)
    {
        compileOptions = CompileOptions.standardCompileOptions.call();
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