package ch.fhnw.thga;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.services.LanguageClient;

import ch.fhnw.thga.TypeSignature.TArrayList;
import frege.interpreter.FregeInterpreter.TMessage;
import frege.repl.FregeRepl.TReplResult;

public class FregeDiagnosticService
{
    
	private static Diagnostic mapMessageToDiagnostic(TMessage message)
    {
		String compilerMessage = message.mem$text.call();
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

	private static List<Diagnostic> getCompilerDiagnostics(TReplResult result)
    {
		if (result.asReplInfo() == null) 
        {
            return Collections.emptyList();
        }
		ArrayList<TMessage> messages = performUnsafe(
            TArrayList
            .fromFregeList(result.asReplInfo().mem1.call()))
			.call();
		return messages
            .stream()
            .map(message -> mapMessageToDiagnostic(message))
            .collect(Collectors.toList());
	}

	static void publishCompilerDiagnostics(
        LanguageClient client, 
        TReplResult result,
        String documentUri)
    {
		CompletableFuture.runAsync(() -> 
        {
            client.publishDiagnostics(new PublishDiagnosticsParams(
                documentUri, getCompilerDiagnostics(result)));
        });
	}
}
