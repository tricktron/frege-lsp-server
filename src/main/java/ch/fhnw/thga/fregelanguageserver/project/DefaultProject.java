package ch.fhnw.thga.fregelanguageserver.project;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import frege.compiler.types.Global.TOptions;

public class DefaultProject implements Project
{

    @Override
    public TOptions getCompileOptions()
    {
        return CompileService.STANDARD_COMPILE_OPTIONS;
    }
}
