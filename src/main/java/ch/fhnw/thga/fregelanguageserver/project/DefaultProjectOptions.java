package ch.fhnw.thga.fregelanguageserver.project;

public class DefaultProjectOptions extends ProjectOptions
{
    @Override
    public Project createProject(String projectRootPath)
    {
        return new DefaultProject();
    }
    
}
