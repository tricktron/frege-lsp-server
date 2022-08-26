package ch.fhnw.thga.fregelanguageserver.compile;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;
import frege.run8.Thunk;

public class CompileService
{
    public static final TOptions STANDARD_COMPILE_OPTIONS = 
        CompileExecutorLSP.standardCompileOptionsLSP.call();
    
    public static final String ROOT_OUTPUT_DIR = CompileExecutorLSP.rootOutputDirLSP;
    private HashMap<URI, TGlobal> uriGlobals;

    final Consumer<TGlobal> updateUriGlobals = new Consumer<TGlobal>() 
    {
        @Override
        public final void accept(TGlobal global)
        {
            URI uri = Path.of(global.mem$options.mem$source).normalize().toUri();
            uriGlobals.put(uri, global);
        }
    };

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

    public CompileService()
    {
        uriGlobals = new HashMap<>();
    }

    public void removeGlobal(URI uri)
    {
        uriGlobals.remove(uri);
    }

    public TGlobal getGlobal(URI uri)
    {
        return uriGlobals.get(uri);
    }

    public List<URI> getAllFileURIs()
    {
        return uriGlobals.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    public void compileAndUpdateGlobals(URI uri, TGlobal projectGlobal)
    {
        List<TGlobal> newGlobals = compileWithMakeMode(uri.getPath(), projectGlobal);
        newGlobals.forEach(updateUriGlobals);
    }

    public static void shutdownCompilerExecutorService()
    {
        performUnsafe(frege.control.Concurrent.shutdown.call());
    }
}
