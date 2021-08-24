package ch.fhnw.thga;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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
	private Lazy<TReplEnv> replEnv;

	public FregeTextDocumentService(FregeLanguageServer server) {
		simpleLanguageServer = server;
		currentOpenFileContents = "";
		replEnv = performUnsafe(TypeSignature.initialReplEnv.call());
	}

	protected static String extractFirstWordFromLine(String fregeFile, int line) {
		Optional<String> functionName = fregeFile.lines().skip(line).findFirst().filter(l -> !l.isEmpty())
				.map(l -> l.substring(0, l.indexOf(" ")));
		return functionName.isEmpty() ? "" : functionName.get().trim();
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

	protected static MarkupContent createFregeTypeSignatureCodeBlock(String typeSignature) {
		return new MarkupContent(MarkupKind.MARKDOWN,
				String.format("```%s\n%s\n```", FREGE_LANGUAGE_ID, typeSignature));
	}

	@Override
	public CompletableFuture<Hover> hover(HoverParams params) {
		String functionName = extractFirstWordFromLine(currentOpenFileContents, params.getPosition().getLine());
		Optional<String> functionSignature = getFunctionTypeSignature(functionName, replEnv);
		if (functionSignature.isEmpty()) {
			return CompletableFuture.completedFuture(null);
		} else {
			return CompletableFuture
					.completedFuture(new Hover(createFregeTypeSignatureCodeBlock(functionSignature.get())));
		}
	}

	private static Diagnostic mapMessageToDiagnostic(TMessage message) {
		int line = message.mem$pos.call().mem$first.mem$line;
		int col = message.mem$pos.call().mem$first.mem$col;
		short messageType = message.mem$msgType.call();
		String compilerMessage = message.mem$text.call();
		return new Diagnostic(new Range(new Position(line, col), new Position(line, col + 1)), compilerMessage,
				DiagnosticSeverity.forValue(messageType), "fregeCompiler");
	}

	private void publishCompilerDiagnostics(TReplResult result, String documentUri) {
		if (result.asReplInfo() != null) {
			ArrayList<TMessage> messages = performUnsafe(TArrayList.fromFregeList(result.asReplInfo().mem1.call()))
					.call();
			List<Diagnostic> compilerDiagnostics = messages.stream()
					.map(FregeTextDocumentService::mapMessageToDiagnostic).collect(Collectors.toList());
			CompletableFuture.runAsync(() -> simpleLanguageServer.client
					.publishDiagnostics(new PublishDiagnosticsParams(documentUri, compilerDiagnostics)));
		}
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		currentOpenFileContents = params.getTextDocument().getText();
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
		replEnv = resEnvTuple.mem2;
		publishCompilerDiagnostics(resEnvTuple.mem1.call(), params.getTextDocument().getUri());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		List<TextDocumentContentChangeEvent> changes = params.getContentChanges();
		currentOpenFileContents = changes.get(changes.size() - 1).getText();
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		currentOpenFileContents = "";
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		TTuple2<TReplResult, TReplEnv> resEnvTuple = performUnsafe(
				TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
		replEnv = resEnvTuple.mem2;
		publishCompilerDiagnostics(resEnvTuple.mem1.call(), params.getTextDocument().getUri());
	}
}
