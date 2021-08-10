package ch.fhnw.thga;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import frege.compiler.types.Global.TGlobal;
import frege.control.arrow.Kleisli;
import frege.control.arrow.Kleisli.TKleisli;
import frege.control.monad.State;
import frege.control.monad.State.TStateT;
import frege.interpreter.FregeInterpreter;
import frege.interpreter.FregeInterpreter.TInterpreterConfig;
import frege.interpreter.javasupport.InterpreterClassLoader;
import frege.prelude.PreludeBase;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run8.Func.U;
import frege.runtime.Phantom.RealWorld;

public class TypeSignatureTest {

    @Test
    void givenSimpleFunctionThenInfersType() {
        String function = "complete i = (i, \"Frege rocks!)\"";
        String expected = "a -> (a,String)";

        TKleisli<TStateT<InterpreterClassLoader, TStateT<TGlobal, U<RealWorld, ?>, ?>, ?>, TInterpreterConfig, String> interpreterString = TypeSignature
                .getTypeSignature(Thunk.lazy(function)).call();
        // how to get the string out of the Interpreter monad, is that even possible in java?
        //actual = interpreterString.????

        assertEquals(expected, actual);
    }

}
