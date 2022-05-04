package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentItem;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class FregeDiagnosticServiceTest
{
    private static final String ERROR_SOURCE = "fregeCompiler";
    
    static Path getPathFromTestResources(String filename) throws InvalidPathException
    {
        return Paths.get("src/test/resources", filename);
    }

    static String readFileFromTestResources(String filename) throws IOException
    {
        return new String(
            Files.readAllBytes(getPathFromTestResources(filename)),
            StandardCharsets.UTF_8);
    }

    static TextDocumentItem readFileToTextDocumentItem(String filename) throws IOException
    {
        String uri = getPathFromTestResources(filename).toUri().toString();
        return new TextDocumentItem(
            uri, 
            FregeTextDocumentService.FREGE_LANGUAGE_ID,
            1,
            readFileFromTestResources(filename)
        );
    }

    private static final Range createRange(
        int fromLine, int fromColumn, int toLine, int toColumn)
    {
        return new Range(
            new Position(fromLine, fromColumn),
            new Position(toLine, toColumn)
        );
    }
    
    @Test
    void should_publish_all_simple_errors_as_diagnostics_given_compiler_errors()
        throws Exception
    {
        FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
        FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
        TextDocumentItem faultyFregeFile = 
            readFileToTextDocumentItem("src/main/frege/ch/fhnw/thga/FaultyFregeTest.fr");
        ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
            ArgumentCaptor.forClass(PublishDiagnosticsParams.class);

        service.didOpen(new DidOpenTextDocumentParams(faultyFregeFile));
        
        List<Diagnostic> expectedErrorDiagnostics = List.of(
            new Diagnostic(
                createRange(7, 0, 7, 11),
                "String is not an instance of Num",
                DiagnosticSeverity.Error,
                ERROR_SOURCE),
            new Diagnostic(
                createRange(5, 0, 5, 22),
                "String is not an instance of Num",
                DiagnosticSeverity.Error,
                ERROR_SOURCE)
        );
        Mockito
            .verify(fregeLSPDTO.getClient(), timeout(1000))
            .publishDiagnostics(diagnosticCaptor.capture());
        PublishDiagnosticsParams expectedErrors = 
            new PublishDiagnosticsParams(faultyFregeFile.getUri(), expectedErrorDiagnostics);
        assertEquals(expectedErrors, diagnosticCaptor.getValue());
    } 
}
