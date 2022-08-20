package ch.fhnw.thga.fregelanguageserver.project;

import frege.compiler.types.Global.TOptions;

abstract class ProjectOptions
{
    TOptions getCompileOptions(String projectRootPath)
    {
        Project project = createProject(projectRootPath);
        return project.getCompileOptions();
    }

    abstract Project createProject(String projectRootPath);
}
