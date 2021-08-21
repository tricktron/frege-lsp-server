package ch.fhnw.thga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.JsonPrimitive;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
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
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

import ch.fhnw.thga.TypeSignature.TArrayList;
import frege.interpreter.FregeInterpreter.TMessage;
import frege.prelude.PreludeBase;
import frege.prelude.PreludeBase.TMaybe;
import frege.prelude.PreludeBase.TMaybe.DJust;
import frege.prelude.PreludeBase.TTuple2;
import frege.repl.FregeRepl.TReplEnv;
import frege.repl.FregeRepl.TReplResult;
import frege.run8.Lazy;
import frege.run8.Thunk;

public class FregeService implements TextDocumentService {
	public static final String FREGE_LANGUAGE_ID = "frege";
	private final SimpleLanguageServer simpleLanguageServer;
	private String currentOpenFileContents;
	private Lazy<TReplEnv> replEnv;

	public FregeService(SimpleLanguageServer server) {
		simpleLanguageServer = server;
		currentOpenFileContents = "";
		replEnv = PreludeBase.TST.performUnsafe(TypeSignature.initialReplEnv.call());
	}

	protected static CompletionItem createTextCompletionItem(String label, Object data) {
		CompletionItem item = new CompletionItem(label);
		item.setKind(CompletionItemKind.Text);
		item.setData(data);
		return item;
	}

	protected static Diagnostic mapMatchResultToDiagnostic(int lineNumber, String match, int startCol, int endCol) {
		return new Diagnostic(new Range(new Position(lineNumber, startCol), new Position(lineNumber, endCol)),
				match + " is all uppercase.", DiagnosticSeverity.Warning, "ex");
	}

	protected static List<Diagnostic> findUpperCaseWordsWithLengthTwoOrMore(String text) {
		String[] lines = text.lines().toArray(String[]::new);
		List<Diagnostic> res = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\b[A-Z]{2,}\\b");
		for (int i = 0; i < lines.length; i++) {
			Matcher matcher = pattern.matcher(lines[i]);
			while (matcher.find()) {
				res.add(mapMatchResultToDiagnostic(i, matcher.group(), matcher.start(), matcher.end()));
			}
		}
		return res;
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
		TTuple2<Integer, String> tuple = Completion.complete(Thunk.lazy(42));
		List<CompletionItem> completionItems = Arrays.asList(createTextCompletionItem("TypeScript", 1),
				createTextCompletionItem("JavaScript", 2),
				createTextCompletionItem(tuple.mem2.call(), tuple.mem1.call()));
		return CompletableFuture.completedFuture(Either.forLeft(completionItems));
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem item) {
		JsonPrimitive data = (JsonPrimitive) item.getData();
		if (data.getAsInt() == 1) {
			item.setDetail("TypeScript details");
			item.setDocumentation("TypeScript documentation");
			return CompletableFuture.completedFuture(item);
		} else if (data.getAsInt() == 2) {
			item.setDetail("JavaScript details");
			item.setDocumentation("JavaScript documentation");
			return CompletableFuture.completedFuture(item);
		} else {
			return CompletableFuture.completedFuture(item);
		}
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
			TTuple2<TMaybe<String>, TReplEnv> maybeSignature = PreludeBase.TST
					.performUnsafe(TypeSignature.getFunctionTypeSignature(Thunk.lazy(functionName), replEnv).call())
					.call().call();
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
			return null;
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
			ArrayList<TMessage> messages = PreludeBase.TST
					.performUnsafe(TArrayList.fromFregeList(result.asReplInfo().mem1.call())).call();
			List<Diagnostic> compilerDiagnostics = messages.stream().map(FregeService::mapMessageToDiagnostic)
					.collect(Collectors.toList());
			CompletableFuture.runAsync(() -> simpleLanguageServer.client
					.publishDiagnostics(new PublishDiagnosticsParams(documentUri, compilerDiagnostics)));
		}
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		currentOpenFileContents = params.getTextDocument().getText();
		TTuple2<TReplResult, TReplEnv> resEnvTuple = PreludeBase.TST
				.performUnsafe(TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
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
		TTuple2<TReplResult, TReplEnv> resEnvTuple = PreludeBase.TST
				.performUnsafe(TypeSignature.evalFregeFile(Thunk.lazy(currentOpenFileContents), replEnv)).call();
		replEnv = resEnvTuple.mem2;
		publishCompilerDiagnostics(resEnvTuple.mem1.call(), params.getTextDocument().getUri());
	}
}
