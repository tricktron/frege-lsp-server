package ch.fhnw.thga.fregelanguageserver.project;

class DefaultProjectOptions extends ProjectOptions
{
    @Override
    Project createProject(String projectRootPath)
    {
        return new DefaultProject();
    }
}
