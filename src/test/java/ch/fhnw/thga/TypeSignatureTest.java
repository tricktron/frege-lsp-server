package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import frege.prelude.PreludeBase;
import frege.run8.Thunk;

public class TypeSignatureTest {

    @Test
    void givenSimpleStringExpressionThenInfersType() {
        String stringExpression = "\"Frege rocks\"";
        String expected = "String";

        String actual = PreludeBase.TST
                .performUnsafe(TypeSignature.evalTypeSignature(Thunk.lazy(stringExpression)).call()).call();

        assertEquals(expected, actual);
    }

}
