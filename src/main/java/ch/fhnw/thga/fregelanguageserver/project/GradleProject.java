package ch.fhnw.thga.fregelanguageserver.project;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.fregegradleplugin.tooling.FregeProjectInfo;
import frege.compiler.types.Global.TOptions;

public class GradleProject implements Project
{
    private final FregeProjectInfo projectInfo;

    public GradleProject(FregeProjectInfo projectInfo)
    {
        this.projectInfo = projectInfo;
    }

    @Override
    public TOptions getCompileOptions()
    {
        return CompileService.compileOptionsFromGradle
        (
            projectInfo.getMainSourceDir(),
            projectInfo.getClasspath()
        );
    }
}
