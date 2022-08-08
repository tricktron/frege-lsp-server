package ch.fhnw.thga.fregelanguageserver.project;

import frege.compiler.types.Global.TOptions;

public abstract class ProjectOptions
{
    public TOptions getCompileOptions(String projectRootPath)
    {
        Project project = createProject(projectRootPath);
        return project.getCompileOptions();
    }

    public abstract Project createProject(String projectRootPath);
}
