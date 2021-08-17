package ch.fhnw.thga;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class SimpleLanguageServer implements LanguageServer {

	private TextDocumentService textService;
	private WorkspaceService workspaceService;
	LanguageClient client;

	public SimpleLanguageServer() {
		textService = new FregeService(this);
		workspaceService = new SimpleWorkspaceService();
	}

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		CompletionOptions completionProvider = new CompletionOptions();
		completionProvider.setResolveProvider(Boolean.TRUE);
		res.getCapabilities().setCompletionProvider(completionProvider);
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
		return CompletableFuture.supplyAsync(() -> res);
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return this.textService;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return this.workspaceService;
	}

	public void connectClient(LanguageClient client) {
		this.client = client;
	}
}