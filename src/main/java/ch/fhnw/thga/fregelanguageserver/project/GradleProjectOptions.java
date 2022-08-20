package ch.fhnw.thga.fregelanguageserver.project;

import java.io.File;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import ch.fhnw.thga.fregegradleplugin.tooling.FregeProjectInfo;

class GradleProjectOptions extends ProjectOptions
{
    @Override
    Project createProject(String projectRootPath)
    {
        try 
        (
            ProjectConnection connection = GradleConnector.newConnector()
            .forProjectDirectory(new File(projectRootPath))
            .connect()
        ) 
        {
            return new GradleProject(connection.getModel(FregeProjectInfo.class));
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return new DefaultProject();
        }
    }
}