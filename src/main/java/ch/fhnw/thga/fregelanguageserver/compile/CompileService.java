package ch.fhnw.thga.fregelanguageserver.compile;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.List;

import frege.compiler.types.Global.TGlobal;
import frege.run8.Thunk;

public class CompileService
{
    public static final TGlobal STANDARD_GLOBAL = performUnsafe
        (CompileExecutorLSP.standardCompileGlobalLSP.call()).call();

    public static List<TGlobal> compileWithMakeMode(String filePath, TGlobal global)
    {
        return performUnsafe
        (
            CompileExecutorLSP.compileMakeLSP
            (
                Thunk.lazy(filePath),
                global
            )
        ).call();
    }

    
}
