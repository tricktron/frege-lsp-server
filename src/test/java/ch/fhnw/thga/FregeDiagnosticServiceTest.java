package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FregeDiagnosticServiceTest
{
    private static final Range createRange(
        int fromLine, int fromColumn, int toLine, int toColumn)
    {
        return new Range(
            new Position(fromLine, fromColumn),
            new Position(toLine, toColumn)
        );
    }
    
    @Test
    void should_convert_single_compile_error_to_diagnostic()
    {
        String faultyFregeCode = String.join(
            "\n",
            "module ch.fhnw.thga.FaultyFregeTest where",
            "",
            "err = 42 + \"42\"");
            List<Diagnostic> expectedErrorDiagnostic = List.of(
                new Diagnostic(
                    createRange(2, 9, 2, 10),
                    "String is not an instance of Num",
                    DiagnosticSeverity.Error,
                    "frege compiler"
                )
            );

            List<Diagnostic> actual = FregeDiagnosticService.getCompilerDiagnostic(faultyFregeCode);
            assertEquals(expectedErrorDiagnostic, actual);
    } 
}
