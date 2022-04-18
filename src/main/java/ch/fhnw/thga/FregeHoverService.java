package ch.fhnw.thga;

import static frege.prelude.PreludeBase.TST.performUnsafe;
import static ch.fhnw.thga.FregeTextDocumentService.FREGE_LANGUAGE_ID;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;

import frege.prelude.PreludeBase.TMaybe;
import frege.prelude.PreludeBase.TTuple2;
import frege.prelude.PreludeBase.TMaybe.DJust;
import frege.repl.FregeRepl.TReplEnv;
import frege.run8.Lazy;
import frege.run8.Thunk;

final class FregeHoverService
{
	private static String findFirstWordFromLine(String line)
    {
		Pattern pattern = Pattern.compile("\\b\\w+\\b");
		Matcher matcher = pattern.matcher(line);
		return matcher.find() ? matcher.group() : "";
	}

	static String extractFirstWordFromLine(String fregeFile, int line)
    {
		Optional<String> functionName = fregeFile
            .lines().
            skip(line)
            .findFirst()
			.map(FregeHoverService::findFirstWordFromLine);
		return functionName.isEmpty() ? "" : functionName.get();
	}

	private static Optional<String> getFunctionTypeSignature(
        String functionName,
        Lazy<TReplEnv> replEnv)
    {
		if (functionName.isEmpty())
        {
			return Optional.empty();
        }
		TTuple2<TMaybe<String>, TReplEnv> maybeSignature = performUnsafe(
					TypeSignature.getFunctionTypeSignature(
                        Thunk.lazy(functionName), replEnv)
                        .call())
                        .call()
                        .call();
		DJust<String> actual = maybeSignature.mem1.call().asJust();
		return actual == null ? Optional.empty() : Optional.of(actual.mem1.call());
	}

	private static String fregeFunctionTypeSignature(String functionName, String typeSignature)
    {
		return String.format("%s :: %s", functionName, typeSignature);
	}

	static MarkupContent createFregeCodeBlock(String fregeCode)
    {
		return new MarkupContent(
            MarkupKind.MARKDOWN, 
            String.format(
                "```%s\n%s\n```", 
                FREGE_LANGUAGE_ID,
                fregeCode)
            );
	}

	static Hover getFunctionTypeSignatureOnLine(
        String fregeCode,
        int line, 
        Lazy<TReplEnv> replEnv)
    {
		String functionName = extractFirstWordFromLine(fregeCode, line);
		Optional<String> functionSignature = getFunctionTypeSignature(functionName, replEnv);
		if (functionSignature.isEmpty())
        {
			return null;
		}
		return new Hover(createFregeCodeBlock(
            fregeFunctionTypeSignature(functionName, functionSignature.get())));
	}
}
    
