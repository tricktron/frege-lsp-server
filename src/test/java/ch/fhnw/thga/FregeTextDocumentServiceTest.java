package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FregeTextDocumentServiceTest
{
    private static final String CORRECT_FREGE_FILENAME    = "CorrectFregeTest.fr";
    private static final String FAULTY_FREGE_FILENAME     = "FaultyFregeTest.fr";
    private static final String ERROR_SOURCE              = "fregeCompiler";

    static Path getPathFromTestResources(String filename) throws InvalidPathException
    {
        return Paths.get(String.join("/", "src/test/resources", filename));
    }

    static String readFileFromTestResources(String filename) throws IOException
    {
        return new String(
            Files.readAllBytes(getPathFromTestResources(filename)),
            StandardCharsets.UTF_8);
    }

    private static final Range createRange(
        int fromLine, int fromColumn, int toLine, int toColumn)
    {
        return new Range(
            new Position(fromLine, fromColumn),
            new Position(toLine, toColumn)
        );
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

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Given_opened_correct_frege_file
    {
        @ParameterizedTest(name = "{0} type signature = {1}")
        @MethodSource("correctFregeFileTypeSignatures")
        void should_return_type_signature_when_hovering_over_function_name(
            Position position,
            String expectedTypeSignature
        ) throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem correctFregeFile = readFileToTextDocumentItem(CORRECT_FREGE_FILENAME);
            HoverParams hoverParams = new HoverParams(
                new TextDocumentIdentifier(correctFregeFile.getUri()),
                position
            );

            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));
            CompletableFuture<Hover> actual = service.hover(hoverParams);
            
            CompletableFuture<Hover> expected = CompletableFuture.completedFuture(
                new Hover(FregeHoverService.createFregeCodeBlock(expectedTypeSignature))
            );
            if (expectedTypeSignature == null) {
                assertNull(actual.get());
            } else {
                assertEquals(expected.get(), actual.get());
            }
        }

        Stream<Arguments> correctFregeFileTypeSignatures() throws IOException
        {
            String[] expectedFunctionSignatures = new String[]
            { 
                null,
                null, 
                "complete :: a -> (a,String)", 
                null, 
                "answerToEverything :: Int",
                null,
                "square :: Num a => a -> a",
                null
            };
            String fregeCode = readFileFromTestResources(CORRECT_FREGE_FILENAME);
            int[] lengthOfLines = fregeCode
                .lines()
                .mapToInt(line -> line.length())
                .toArray();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return IntStream
                .range(0, (int) fregeCode.lines().count())
                .mapToObj(line -> new Position(line, random.nextInt(0, lengthOfLines[line] + 1)))
                .map(pos -> arguments(pos, expectedFunctionSignatures[pos.getLine()]));
        }

        @Test
        void should_support_cancellation_of_hover() throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem correctFregeFile = readFileToTextDocumentItem(CORRECT_FREGE_FILENAME);
            HoverParams hoverParams = new HoverParams(
                new TextDocumentIdentifier(correctFregeFile.getUri()),
                new Position(2, 2)
            );

            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));
            CompletableFuture<Hover> actual = service.hover(hoverParams);
            actual.cancel(true);
            
            assertThrows(CancellationException.class, () -> {
                assertNull(actual.get(1, TimeUnit.SECONDS));
            });
        }

        @Test
        void should_not_publish_any_errors_as_diagnostics() throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem correctFregeFile = readFileToTextDocumentItem(CORRECT_FREGE_FILENAME);
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);
            
            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));

            PublishDiagnosticsParams expected = new PublishDiagnosticsParams(
                correctFregeFile.getUri(),
                Collections.emptyList()
            );
            Mockito
                .verify(fregeLSPDTO.getClient(), timeout(1000))
                .publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expected, diagnosticCaptor.getValue());
        }

        @Test
        void should_not_publish_any_errors_as_diagnostics_when_no_changes_and_did_save()
        throws Exception 
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem correctFregeFile = readFileToTextDocumentItem(CORRECT_FREGE_FILENAME);
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);
            
            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));
            service.didSave(new DidSaveTextDocumentParams(
                    new TextDocumentIdentifier(correctFregeFile.getUri()))
            );
            
            PublishDiagnosticsParams expected = new PublishDiagnosticsParams(
                correctFregeFile.getUri(),
                Collections.emptyList()
            );
            Mockito
                .verify(fregeLSPDTO.getClient(), timeout(1000)
                .times(2))
                .publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expected, diagnosticCaptor.getValue());
        }

        @Test
        @Disabled
        void should_publish_all_errors_as_diagnostics_when_error_changes_and_did_save()
        throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem correctFregeFile = readFileToTextDocumentItem(CORRECT_FREGE_FILENAME);
            VersionedTextDocumentIdentifier id = new VersionedTextDocumentIdentifier(
                correctFregeFile.getUri(),
                correctFregeFile.getVersion()
            );
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);
            
            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(
                id,
                List.of(new TextDocumentContentChangeEvent(
                    readFileFromTestResources(FAULTY_FREGE_FILENAME)))
            );

            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));
            service.didChange(params);
            service.didSave(new DidSaveTextDocumentParams(
                new TextDocumentIdentifier(correctFregeFile.getUri()))
            );

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
            PublishDiagnosticsParams expectedErrors = 
                new PublishDiagnosticsParams(correctFregeFile.getUri(), expectedErrorDiagnostics);
            Mockito
                .verify(fregeLSPDTO.getClient(), timeout(1000)
                .times(2))
                .publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @ExtendWith(MockitoExtension.class)
    class Given_opened_faulty_frege_file
    {
        @Test
        @Disabled
        void should_publish_all_simple_errors_as_diagnostics() throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem faultyFregeFile = readFileToTextDocumentItem(FAULTY_FREGE_FILENAME);
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

        @Test
        @Disabled
        void should_publish_all_type_errors_as_diagnostics() throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem faultyFregeFile = readFileToTextDocumentItem("TypeError.fr");
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);

            service.didOpen(new DidOpenTextDocumentParams(faultyFregeFile));
            
            List<Diagnostic> expectedErrorDiagnostics = List.of(
                new Diagnostic(
                    createRange(11, 0, 11, 36),
                    String.join("\n",
                        "type error in expression",
                        "n",
                        "type is : Integer",
                        "expected: Int"),
                    DiagnosticSeverity.Error,
                    ERROR_SOURCE
                ),
                new Diagnostic(
                    createRange(11, 0, 11, 43),
                    String.join("\n",
                        "type error in expression",
                        "reducedDigitSum $ digitSum n",
                        "type is : Int",
                        "expected: Integer"),
                    DiagnosticSeverity.Error,
                    ERROR_SOURCE
                ),
                new Diagnostic(
                    createRange(11, 0, 11, 24),
                    String.join("\n",
                        "type error in expression",
                        "n < 10 then n else reducedDigitSum $ digitSum n",
                        "type is : Integer",
                        "expected: Int"),
                    DiagnosticSeverity.Error,
                    ERROR_SOURCE
                ),
                new Diagnostic(
                    createRange(11, 0, 11, 17),
                    String.join("\n",
                        "type error in expression",
                        "n = if n < 10 then n else reducedDigitSum $ digitSum n",
                        "type is : Integer",
                        "expected: Int"),
                    DiagnosticSeverity.Error,
                    ERROR_SOURCE
                )
            );
            Mockito
                .verify(fregeLSPDTO.getClient(), timeout(1000))
                .publishDiagnostics(diagnosticCaptor.capture());
            PublishDiagnosticsParams expectedErrors = 
                new PublishDiagnosticsParams(faultyFregeFile.getUri(), expectedErrorDiagnostics);
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }

        @Test
        @Disabled
        void should_publish_all_errors_as_diagnostics_when_no_changes_and_did_save()
        throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem faultyFregeFile = readFileToTextDocumentItem(FAULTY_FREGE_FILENAME);
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);

            service.didOpen(new DidOpenTextDocumentParams(faultyFregeFile));
            service.didSave(new DidSaveTextDocumentParams(
                    new TextDocumentIdentifier(faultyFregeFile.getUri()))
            );
            
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
                .verify(fregeLSPDTO.getClient(), timeout(1000)
                .times(2))
                .publishDiagnostics(diagnosticCaptor.capture());
            PublishDiagnosticsParams expectedErrors = 
                new PublishDiagnosticsParams(faultyFregeFile.getUri(), expectedErrorDiagnostics);
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }

        @Test
        void should_not_publish_any_errors_as_diagnostics_when_correcting_changes_and_did_save()
        throws Exception
        {
            FregeLSPDTO fregeLSPDTO = new FregeLSPDTO();
            FregeTextDocumentService service = new FregeTextDocumentService(fregeLSPDTO.getServer());
            TextDocumentItem faultyFregeFile = readFileToTextDocumentItem(FAULTY_FREGE_FILENAME);
            VersionedTextDocumentIdentifier id = new VersionedTextDocumentIdentifier(
                faultyFregeFile.getUri(),
                faultyFregeFile.getVersion()
            );
            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(
                id,
                List.of(new TextDocumentContentChangeEvent(
                    readFileFromTestResources(CORRECT_FREGE_FILENAME)))
            );
            PublishDiagnosticsParams expected = new PublishDiagnosticsParams(
                faultyFregeFile.getUri(),
                Collections.emptyList()
            );
            ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor = 
                ArgumentCaptor.forClass(PublishDiagnosticsParams.class);

            service.didOpen(new DidOpenTextDocumentParams(faultyFregeFile));
            service.didChange(params);
            service.didSave(new DidSaveTextDocumentParams(
                new TextDocumentIdentifier(faultyFregeFile.getUri()))
            );

            Mockito
                .verify(fregeLSPDTO.getClient(), timeout(1000)
                .times(2))
                .publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expected, diagnosticCaptor.getValue());
        }
    }
}
