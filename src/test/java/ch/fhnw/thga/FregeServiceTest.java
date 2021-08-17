package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

@TestInstance(Lifecycle.PER_CLASS)
class FregeServiceTest {

    @Mock
    SimpleLanguageServer server;
    FregeService service = new FregeService(server);

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
    void setup() {
        service = new FregeService(server);
    }

    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Given_frege_file {

        private static final String FREGE_FILENAME = "FunctionNameTest.fr";

        private String fregeFileContents;
        private TextDocumentItem fregeFile;

        @BeforeAll
        void setup() throws Exception {
            fregeFile = readFregeFile(FREGE_FILENAME);
            fregeFileContents = readFileFromTestResources(FREGE_FILENAME);
            service.didOpen(new DidOpenTextDocumentParams(fregeFile));
        }

        Stream<Arguments> linesAndExpectedPair(int linesCount, List<String> expected) {
            return IntStream.range(0, linesCount).mapToObj(i -> arguments(i, expected.get(i)));
        }

        Stream<Arguments> expectedFirstWords() {
            List<String> expectedFirstWords = List.of("module", "", "complete", "", "answerToEverything", "", "square",
                    "");
            return IntStream.range(0, (int) fregeFileContents.lines().count())
                    .mapToObj(i -> arguments(i, expectedFirstWords.get(i)));
        }

        @ParameterizedTest(name = "Line = {0} first word = {1}")
        @MethodSource("expectedFirstWords")
        @DisplayName("then can extract first word on each line")
        void then_can_extract_first_word_on_each_line(int line, String expected) throws Exception {
            String actual = FregeService.extractFirstWordFromLine(fregeFileContents, line);
            assertEquals(expected, actual);
        }

        @ParameterizedTest(name = "{0} type signature = {1}")
        @MethodSource("expectedFunctionSignatures")
        @DisplayName("when hovering over function name then returns type signature")
        void when_hovering_over_function_name_then_returns_type_signature(Position position,
                String expectedTypeSignature) throws Exception {
            HoverParams hoverParams = new HoverParams(new TextDocumentIdentifier(fregeFile.getUri()), position);
            CompletableFuture<Hover> expected = CompletableFuture
                    .completedFuture(new Hover(FregeService.createFregeTypeSignatureCodeBlock(expectedTypeSignature)));
            CompletableFuture<Hover> actual = service.hover(hoverParams);
            if (expectedTypeSignature == null) {
                assertNull(actual);
            } else {
                assertEquals(expected.get(), actual.get());
            }
        }

        Stream<Arguments> expectedFunctionSignatures() {
            String[] expectedFunctionSignatures = new String[] { null, null, "a -> (a,String)", null, "Int", null,
                    "Num a => a -> a", null };
            int[] lengthOfLines = fregeFileContents.lines().mapToInt(line -> line.length()).toArray();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            return IntStream.range(0, (int) fregeFileContents.lines().count())
                    .mapToObj(line -> new Position(line, random.nextInt(0, lengthOfLines[line] + 1)))
                    .map(pos -> arguments(pos, expectedFunctionSignatures[pos.getLine()]));

        }
    }
}
