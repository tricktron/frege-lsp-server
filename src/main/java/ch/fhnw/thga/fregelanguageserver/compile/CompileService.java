package ch.fhnw.thga.fregelanguageserver.compile;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.List;

import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;
import frege.run8.Thunk;

public class CompileService
{
    public static final TOptions STANDARD_COMPILE_OPTIONS = 
        CompileExecutorLSP.standardCompileOptionsLSP.call();
    
    public static final String ROOT_OUTPUT_DIR = CompileExecutorLSP.rootOutputDirLSP;

    public static TGlobal createCompileGlobal(TOptions options)
    {
        return performUnsafe(CompileExecutorLSP.fromOptionsLSP(options)).call();
    }

    public static TOptions compileOptionsFromGradle
        (
            String srcMainDir, 
            String extraClasspath
        )
    {
        return CompileExecutorLSP.fromGradle
        (
            Thunk.lazy(srcMainDir), 
            Thunk.lazy(extraClasspath)
        );
    }
    
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
