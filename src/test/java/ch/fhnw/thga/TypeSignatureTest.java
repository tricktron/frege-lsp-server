package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import frege.prelude.PreludeBase;
import frege.repl.FregeRepl.TReplResult;
import frege.run8.Thunk;

public class TypeSignatureTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    private static String readFileFromTestResources(String filename) throws IOException {
        String path = String.join("/", TEST_RESOURCES_PATH, filename);
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    @Test
    void given_frege_module_with_function_then_infers_type() throws Exception {
        String fregeFile = readFileFromTestResources("CompletionTest.fr");
        TReplResult result = PreludeBase.TST.performUnsafe(TypeSignature.getFunctionTypeSignature(Thunk.lazy(fregeFile), Thunk.lazy("complete")).call()).call();
        String actual = result.asReplInfo().mem1.call().asCons().mem1.call().mem$text.call();
        assertEquals("a -> (a,String)", actual);
    }
}
