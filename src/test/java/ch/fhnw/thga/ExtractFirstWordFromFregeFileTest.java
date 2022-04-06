package ch.fhnw.thga;

import static ch.fhnw.thga.FregeTextDocumentService.extractFirstWordFromLine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExtractFirstWordFromFregeFileTest
{
    private static final String NEW_LINE           = System.lineSeparator();
    private static final String FIRST_WORD_TEST_FR = String.join(NEW_LINE,
        "module ch.fhnw.thga.FirstWordTest where",
        "",
        "complete i = (i, \"Frege rocks\")",
        "",
        "answerToEverything = 42",
        "",
        "square x = x * x",
        "",
        "a",
        " b",
        "   c d",
        "       ef gh"
    );
        
    @Test
    void should_return_empty_string_given_file_is_empty()
    {
        assertEquals("", extractFirstWordFromLine("", 0));
        assertEquals("", extractFirstWordFromLine("", 1));
    }

    @ParameterizedTest(name = "first word = {1} line = {0}")
    @MethodSource("expectedFirstWords")
    @DisplayName("should return first word given FirstWordTest.fr file")
    void given_first_word_test_file(int line, String expected)
    {
        String actual = extractFirstWordFromLine(FIRST_WORD_TEST_FR, line);
        assertEquals(expected, actual);
    }
    
    private static final Stream<Arguments> expectedFirstWords()
    {
        List<String> expectedFirstWords = List.of(
            "module", 
            "", 
            "complete", 
            "", 
            "answerToEverything", 
            "", 
            "square",
            "", 
            "a", 
            "b", 
            "c", 
            "ef", 
            "bla"
        );
        return IntStream
            .range(
                0, 
                (int) FIRST_WORD_TEST_FR.lines().count()
            )
            .mapToObj(i -> arguments(i, expectedFirstWords.get(i)));
    }
}
