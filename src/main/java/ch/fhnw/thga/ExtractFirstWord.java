package ch.fhnw.thga;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ExtractFirstWord
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
            .lines()
            .skip(line)
            .findFirst()
			.map(ExtractFirstWord::findFirstWordFromLine);
		return functionName.isEmpty() ? "" : functionName.get();
	}
}
