package ch.fhnw.thga.fregelanguageserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.fregelanguageserver.diagnostic.FregeDiagnosticService;
import ch.fhnw.thga.fregelanguageserver.hover.HoverService;

public class FregeTextDocumentService implements TextDocumentService
{
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
    private List<String> currentOpenFileLines;

	public FregeTextDocumentService(FregeLanguageServer server)
    {
		simpleLanguageServer    = server;
		currentOpenFileContents = "";
        currentOpenFileLines    = new ArrayList<>();
	}

    @Override
	public CompletableFuture<Hover> hover(HoverParams params)
    {
        return HoverService.hover(params, currentOpenFileContents);
    }

	@Override
	public void didOpen(DidOpenTextDocumentParams params)
    {
		currentOpenFileContents = params.getTextDocument().getText();
        currentOpenFileLines    = currentOpenFileContents
            .lines()
            .collect(Collectors.toList());

		FregeDiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.client,
            currentOpenFileContents,
            params.getTextDocument().getUri()
        );
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params)
    {
		List<TextDocumentContentChangeEvent> changes 
                                = params.getContentChanges();
		currentOpenFileContents = changes.get(changes.size() - 1).getText();
        currentOpenFileLines    = currentOpenFileContents
            .lines()
            .collect(Collectors.toList());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params)
    {
		currentOpenFileContents = "";
        currentOpenFileLines.clear();
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params)
    {
		FregeDiagnosticService.publishCompilerDiagnostics(
            simpleLanguageServer.client,
            currentOpenFileContents,
            params.getTextDocument().getUri()
        );
	}
}
