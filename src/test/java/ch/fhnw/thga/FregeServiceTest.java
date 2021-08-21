package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
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
import org.eclipse.lsp4j.services.LanguageClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FregeServiceTest {
    private static final String CORRECT_FREGE_FILENAME = "CorrectFregeTest.fr";
    private static final String FAULTY_FREGE_FILENAME = "FaultyFregeTest.fr";
    private String correctFregeFileContents;
    private String faultyFregeFileContents;
    private List<Diagnostic> expectedErrorDiagnostics;

    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    static Path getPathFromTestResources(String filename) throws InvalidPathException {
        return Paths.get(String.join("/", TEST_RESOURCES_PATH, filename));
    }

    static String readFileFromTestResources(String filename) throws IOException {
        return new String(Files.readAllBytes(getPathFromTestResources(filename)), StandardCharsets.UTF_8);
    }

    static TextDocumentItem readFregeFile(String filename) throws IOException {
        String uri = getPathFromTestResources(filename).toUri().toString();
        return new TextDocumentItem(uri, FregeService.FREGE_LANGUAGE_ID, 1, readFileFromTestResources(filename));
    }

    @BeforeAll
    void setup() throws Exception {
        correctFregeFileContents = readFileFromTestResources(CORRECT_FREGE_FILENAME);
        faultyFregeFileContents = readFileFromTestResources(FAULTY_FREGE_FILENAME);
        final String expectedCompilerMessage = "String is not an instance of Num";
        final String diagnosticSource = "fregeCompiler";
        expectedErrorDiagnostics = List.of(
                new Diagnostic(new Range(new Position(8, 11), new Position(8, 12)), expectedCompilerMessage,
                        DiagnosticSeverity.Error, diagnosticSource),
                (new Diagnostic(new Range(new Position(6, 22), new Position(6, 23)), expectedCompilerMessage,
                        DiagnosticSeverity.Error, diagnosticSource)));
    }

    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Given_opened_correct_frege_file {

        SimpleLanguageServer server;
        LanguageClient client;
        FregeService service;
        private TextDocumentItem correctFregeFile;

        @Captor
        ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor;

        @BeforeAll
        void setup() throws Exception {
            correctFregeFile = readFregeFile(CORRECT_FREGE_FILENAME);
        }

        @BeforeEach
        void init() throws Exception {
            server = spy(SimpleLanguageServer.class);
            client = mock(LanguageClient.class);
            server.connect(client);
            service = new FregeService(server);
            service.didOpen(new DidOpenTextDocumentParams(correctFregeFile));
        }

        Stream<Arguments> expectedFirstWords() {
            List<String> expectedFirstWords = List.of("module", "", "complete", "", "answerToEverything", "", "square",
                    "");
            return IntStream.range(0, (int) correctFregeFileContents.lines().count())
                    .mapToObj(i -> arguments(i, expectedFirstWords.get(i)));
        }

        Stream<Arguments> expectedFunctionSignatures() {
            String[] expectedFunctionSignatures = new String[] { null, null, "a -> (a,String)", null, "Int", null,
                    "Num a => a -> a", null };
            int[] lengthOfLines = correctFregeFileContents.lines().mapToInt(line -> line.length()).toArray();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return IntStream.range(0, (int) correctFregeFileContents.lines().count())
                    .mapToObj(line -> new Position(line, random.nextInt(0, lengthOfLines[line] + 1)))
                    .map(pos -> arguments(pos, expectedFunctionSignatures[pos.getLine()]));

        }

        @ParameterizedTest(name = "Line = {0} first word = {1}")
        @MethodSource("expectedFirstWords")
        @DisplayName("then can extract first word on each line")
        void then_can_extract_first_word_on_each_line(int line, String expected) throws Exception {
            String actual = FregeService.extractFirstWordFromLine(correctFregeFileContents, line);
            assertEquals(expected, actual);
        }

        @ParameterizedTest(name = "{0} type signature = {1}")
        @MethodSource("expectedFunctionSignatures")
        @DisplayName("when hovering over function name then returns type signature")
        void when_hovering_over_function_name_then_returns_type_signature(Position position,
                String expectedTypeSignature) throws Exception {
            HoverParams hoverParams = new HoverParams(new TextDocumentIdentifier(correctFregeFile.getUri()), position);
            CompletableFuture<Hover> expected = CompletableFuture
                    .completedFuture(new Hover(FregeService.createFregeTypeSignatureCodeBlock(expectedTypeSignature)));

            CompletableFuture<Hover> actual = service.hover(hoverParams);
            if (expectedTypeSignature == null) {
                assertNull(actual);
            } else {
                assertEquals(expected.get(), actual.get());
            }
        }

        @Test
        void then_no_compiler_errors_are_published() {
            Mockito.verifyNoInteractions(client);
        }

        @Test
        void when_no_changes_and_did_save_then_no_compiler_errors_are_published() {
            service.didSave(new DidSaveTextDocumentParams(new TextDocumentIdentifier(correctFregeFile.getUri())));
            Mockito.verifyNoInteractions(client);
        }

        @Test
        void when_error_changes_and_did_save_then_compiler_errors_are_published() {
            VersionedTextDocumentIdentifier id = new VersionedTextDocumentIdentifier(correctFregeFile.getUri(),
                    correctFregeFile.getVersion());
            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(id,
                    List.of(new TextDocumentContentChangeEvent(faultyFregeFileContents)));
            PublishDiagnosticsParams expectedErrors = new PublishDiagnosticsParams(correctFregeFile.getUri(),
                    expectedErrorDiagnostics);

            service.didChange(params);
            service.didSave(new DidSaveTextDocumentParams(new TextDocumentIdentifier(correctFregeFile.getUri())));

            Mockito.verify(client, times(1)).publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }
    }

    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @ExtendWith(MockitoExtension.class)
    class Given_opened_faulty_frege_file {

        private TextDocumentItem faultyFregeFile;
        SimpleLanguageServer server;
        LanguageClient client;
        FregeService service;

        @Captor
        ArgumentCaptor<PublishDiagnosticsParams> diagnosticCaptor;

        @BeforeAll
        void setup() throws Exception {
            faultyFregeFile = readFregeFile(FAULTY_FREGE_FILENAME);
        }

        @BeforeEach
        void init() {
            server = spy(SimpleLanguageServer.class);
            client = mock(LanguageClient.class);
            server.connect(client);
            service = new FregeService(server);
            service.didOpen(new DidOpenTextDocumentParams(faultyFregeFile));
        }

        @Test
        void then_publish_all_errors_as_diagnostics() {
            Mockito.verify(client, times(1)).publishDiagnostics(diagnosticCaptor.capture());
            PublishDiagnosticsParams expectedErrors = new PublishDiagnosticsParams(faultyFregeFile.getUri(),
                    expectedErrorDiagnostics);
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }

        @Test
        void when_no_changes_and_did_save_then_publish_all_errors_as_diagnostics() {
            PublishDiagnosticsParams expectedErrors = new PublishDiagnosticsParams(faultyFregeFile.getUri(),
                    expectedErrorDiagnostics);

            service.didSave(new DidSaveTextDocumentParams(new TextDocumentIdentifier(faultyFregeFile.getUri())));
            Mockito.verify(client, times(2)).publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }

        @Test
        void when_correcting_changes_and_did_save_then_no_more_errors_are_published() {
            VersionedTextDocumentIdentifier id = new VersionedTextDocumentIdentifier(faultyFregeFile.getUri(),
                    faultyFregeFile.getVersion());
            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(id,
                    List.of(new TextDocumentContentChangeEvent(correctFregeFileContents)));
            PublishDiagnosticsParams expectedErrors = new PublishDiagnosticsParams(faultyFregeFile.getUri(),
                    expectedErrorDiagnostics);

            service.didChange(params);
            service.didSave(new DidSaveTextDocumentParams(new TextDocumentIdentifier(faultyFregeFile.getUri())));

            Mockito.verify(client, times(1)).publishDiagnostics(diagnosticCaptor.capture());
            assertEquals(expectedErrors, diagnosticCaptor.getValue());
        }
    }
}
