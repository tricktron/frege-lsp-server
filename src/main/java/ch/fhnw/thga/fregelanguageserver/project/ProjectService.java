package ch.fhnw.thga.fregelanguageserver.project;

import static ch.fhnw.thga.fregelanguageserver.compile.CompileService.ROOT_OUTPUT_DIR;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;

public class ProjectService
{
    private static final String LOGFILE_NAME = "frege.log";
    private static final String OUTPUT_DIR_ERROR_MESSAGE = 
    "Could not create output dir %s. Please create it manually and restart the server.";
    private final TGlobal projectGlobal;
    
    public ProjectService(TGlobal projectGlobal)
    {
        this.projectGlobal  = projectGlobal;
    }

    public TGlobal getProjectGlobal()
    {
        return projectGlobal;
    }

    private static TOptions createProjectOptions(Path projectRootPath)
    {
        if (projectRootPath.resolve("build.gradle").toFile().exists())
        {
            return new GradleProjectOptions()
            .getCompileOptions(projectRootPath.toFile().getAbsolutePath());
        }
        return new DefaultProject().getCompileOptions();
    }

    private static void createLogFile(Path projectRootPath)
    {
        try
        {
            Path rootOutputDir = Files
            .createDirectories(projectRootPath.resolve(ROOT_OUTPUT_DIR));
            System.setErr(new PrintStream(new FileOutputStream
            (
                rootOutputDir
                .resolve(LOGFILE_NAME).toFile()
            )));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void createLanguageServerOutputDir(Path outputDirPath)
    {
        try 
        {
            Files.createDirectories(outputDirPath);
        } catch (IOException e) 
        {
            e.printStackTrace();
            throw new RuntimeException(String.format(OUTPUT_DIR_ERROR_MESSAGE, outputDirPath), e);
        }
    }

    public static ProjectService fromRootPath(Path projectRoot)
    {
        createLogFile(projectRoot);
        TOptions projectOptions = createProjectOptions(projectRoot);
        createLanguageServerOutputDir(Paths.get(projectOptions.mem$dir).normalize());
        TGlobal  projectGlobal  = CompileService.createCompileGlobal(projectOptions);
        return new ProjectService(projectGlobal);
    }
}
