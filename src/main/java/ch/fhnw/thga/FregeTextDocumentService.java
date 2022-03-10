package ch.fhnw.thga;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.TypeSignature.TArrayList;
import frege.interpreter.FregeInterpreter.TMessage;
import frege.prelude.PreludeBase.TMaybe;
import frege.prelude.PreludeBase.TMaybe.DJust;
import frege.prelude.PreludeBase.TTuple2;
import frege.repl.FregeRepl.TReplEnv;
import frege.repl.FregeRepl.TReplResult;
import frege.run8.Lazy;
import frege.run8.Thunk;

public class FregeTextDocumentService implements TextDocumentService {
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final FregeLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
    private List<String> currentOpenFileLines;
	private Lazy<TReplEnv> replEnv;

	public FregeTextDocumentService(FregeLanguageServer server) {
		simpleLanguageServer = server;
		currentOpenFileContents = "";
        currentOpenFileLines = new ArrayList<>();

		replEnv = performUnsafe(TypeSignature.initialReplEnv.call());
	}

	private static String findFirstWordFromLine(String line) {
		Pattern pattern = Pattern.compile("\\b\\w+\\b");
		Matcher matcher = pattern.matcher(line);
		return matcher.find() ? matcher.group() : "";
	}

	protected static String extractFirstWordFromLine(String fregeFile, int line) {
		Optional<String> functionName = fregeFile.lines().skip(line).findFirst()
				.map(FregeTextDocumentService::findFirstWordFromLine);
		return functionName.isEmpty() ? "" : functionName.get();
	}

	protected static Optional<String> getFunctionTypeSignature(String functionName, Lazy<TReplEnv> replEnv) {
		if (functionName.isEmpty()) {
			return Optional.empty();
		} else {
			TTuple2<TMaybe<String>, TReplEnv> maybeSignature = performUnsafe(
					TypeSignature.getFunctionTypeSignature(Thunk.lazy(functionName), replEnv).call()).call().call();
			DJust<String> actual = maybeSignature.mem1.call().asJust();
			return actual == null ? Optional.empty() : Optional.of(actual.mem1.call());
		}
	}

	private String fregeFunctionTypeSignature(String functionName, String typeSignature) {
		return String.format("%s :: %s", functionName, typeSignature);
	}

	protected static MarkupContent createFregeCodeBlock(String fregeCode) {
		return new MarkupContent(MarkupKind.MARKDOWN, String.format("```%s\n%s\n```", FREGE_LANGUAGE_ID, fregeCode));
	}

	private Hover getFunctionTypeSignatureOnLine(int line) {
		String functionName = extractFirstWordFromLine(currentOpenFileContents, line);
		Optional<String> functionSignature = getFunctionTypeSignature(functionName, replEnv);
		if (functionSignature.isEmpty()) {
			return null;
		} else {
			return (new Hover(createFregeCodeBlock(fregeFunctionTypeSignature(functionName, functionSignature.get()))));
		}
	}

	@Override
	public CompletableFuture<Hover> hover(HoverParams params) {
		return CompletableFutures.computeAsync(cancel -> {
			if (cancel.isCanceled()) {
				return null;
			} else {
				return getFunctionTypeSignatureOnLine(params.getPosition().getLine());
			}
		});
	}

    private String extractTypeErrorExpressionFromCompilerMessage(String compilerMessage)
    {
        return compilerMessage.lines().skip(1).findFirst().orElse("");
    }

    private int findTypeErrorLineIndex(String faultyExpression)
    {
        return (int) this.currentOpenFileLines
            .stream()
            .takeWhile(line -> !line.contains(faultyExpression))
            .count();
    }

    private Range findTypeErrorRange(String typeErrorMessage)
    {
        String typeErrorExpression = extractTypeErrorExpressionFromCompilerMessage(typeErrorMessage);
        Pattern pattern = Pattern.compile(typeErrorExpression);
        int line = findTypeErrorLineIndex(typeErrorExpression);
        Matcher matcher = pattern
            .matcher(this.currentOpenFileLines.get(line));
        return matcher.find() ? 
            new Range(
                new Position(line, matcher.start()),
                new Position(line, matcher.end()))
            : null;
    }

	private Diagnostic mapMessageToDiagnostic(TMessage message)
    {
		String compilerMessage = message.mem$text.call();
        /*if (compilerMessage.contains("type error in expression"))
        {
            errorRange = findTypeErrorRange(compilerMessage);
        }*/
		int line = message.mem$pos.call().mem$first.mem$line;
		int col = message.mem$pos.call().mem$first.mem$col;
        Range errorRange = new Range(
            new Position(line - 1, 0),
            new Position(line - 1, col)
        );
		return new Diagnostic(
            errorRange,
            compilerMessage,
			DiagnosticSeverity.forValue(message.mem$msgType.call()),
            "fregeCompiler"
        );
	}

	private List<Diagnostic> getCompilerDiagnostics(TReplResult result) {
		if (result.asReplInfo() != null) {
			ArrayList<TMessage> messages = performUnsafe(TArrayList.fromFregeList(result.asReplInfo().mem1.call()))
					.call();
			return messages.stream().map(message -> mapMessageToDiagnostic(message)).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	private void publishCompilerDiagnostics(TReplResult result, String documentUri) {
		CompletableFuture.runAsync(() -> simpleLanguageServer.client
				.publishDiagnostics(new PublishDiagnosticsParams(documentUri, getCompilerDiagnostics(result))));
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		currentOpenFileContents = params.getTextDocument().getText();
        currentOpenFileLines = currentOpenFileContents.lines().collect(Collectors.toList());
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
		replEnv = resEnvTuple.mem2;
		publishCompilerDiagnostics(resEnvTuple.mem1.call(), params.getTextDocument().getUri());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		List<TextDocumentContentChangeEvent> changes = params.getContentChanges();
		currentOpenFileContents = changes.get(changes.size() - 1).getText();
        currentOpenFileLines = currentOpenFileContents.lines().collect(Collectors.toList());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		currentOpenFileContents = "";
        currentOpenFileLines.clear();
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
		replEnv = resEnvTuple.mem2;
		publishCompilerDiagnostics(resEnvTuple.mem1.call(), params.getTextDocument().getUri());
	}
}
