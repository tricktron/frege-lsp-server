package ch.fhnw.thga;

import org.junit.jupiter.api.Test;
import static ch.fhnw.thga.SimpleTextDocumentService.createTextCompletionItem;
import static ch.fhnw.thga.SimpleTextDocumentService.findUpperCaseWordsWithLengthTwoOrMore;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

class AppTest {
    @Test
    void can_create_text_completion_item() {
        String label = "label";
        int data = 1;
        CompletionItem item = createTextCompletionItem(label, data);
        assertEquals(label, item.getLabel());
        assertEquals(data, item.getData());
    }
    @Test
    void given_single_line_string_find_all_uppercase_substrings_with_length_two_or_more() {
        String s = "ANY browsers, ANY OS";
        List<Diagnostic> expected = Arrays.asList(
                new Diagnostic(new Range(new Position(0, 0), new Position(0, 3)), "ANY is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"),
                new Diagnostic(new Range(new Position(0, 14), new Position(0, 17)), "ANY is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"),
                new Diagnostic(new Range(new Position(0, 18), new Position(0, 20)), "OS is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"));
        assertIterableEquals(expected, findUpperCaseWordsWithLengthTwoOrMore(s));
    }

    @Test
    void given_multi_line_string_find_all_uppercase_substrings_with_length_two_or_more() {
        String s = "ANY browser,\nANY OS,\nJUST everywhere";
        List<Diagnostic> expected = Arrays.asList(
                new Diagnostic(new Range(new Position(0, 0), new Position(0, 3)), "ANY is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"),
                new Diagnostic(new Range(new Position(1, 0), new Position(1, 3)), "ANY is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"),
                new Diagnostic(new Range(new Position(1, 4), new Position(1, 6)), "OS is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"),
                new Diagnostic(new Range(new Position(2, 0), new Position(2, 4)), "JUST is all uppercase.",
                        DiagnosticSeverity.Warning, "ex"));
        assertIterableEquals(expected, findUpperCaseWordsWithLengthTwoOrMore(s));
    }
}
