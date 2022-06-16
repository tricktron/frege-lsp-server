package ch.fhnw.thga.fregelanguageserver;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

public class FregeWorkspaceService implements WorkspaceService {

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
	}
}