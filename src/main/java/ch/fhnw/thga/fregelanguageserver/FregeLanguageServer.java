package ch.fhnw.thga.fregelanguageserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.gradleplugins.FregeProjectInfo;
import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;


public class FregeLanguageServer implements LanguageServer, LanguageClientAware 
{
    private static final String LOGFILE_NAME = "frege-ls.log";
	private final FregeTextDocumentService textService;
	private final WorkspaceService workspaceService;
	private LanguageClient client;
    private TGlobal projectGlobal;

	public FregeLanguageServer()
    {
		textService      = new FregeTextDocumentService(this);
		workspaceService = new FregeWorkspaceService();
	}

    public LanguageClient getClient()
    {
        return client;
    }

    public TGlobal getProjectGlobal()
    {
        return projectGlobal;
    }

    private TOptions createProjectOptions(String projectRootPath)
    {
        try 
        (
            ProjectConnection connection = GradleConnector.newConnector()
            .forProjectDirectory(new File(projectRootPath))
            .connect()
        ) 
        {
            FregeProjectInfo fregeProjectInfo = connection.getModel(FregeProjectInfo.class);
            return CompileService.compileOptionsFromGradle
            (
                fregeProjectInfo.getFregeMainSourceDir(),
                fregeProjectInfo.getFregeDependenciesClasspath()
            );
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return CompileService.STANDARD_COMPILE_OPTIONS;
        }
    }

    private String couldNotCreateOutputDirMessage(String outputDirPath)
    {
        return String.format
        (
            "Could not create output dir %s. Please create it manually and restart the server.",
            outputDirPath
        );
    }

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params)
    {
        TOptions projectOptions = createProjectOptions(params.getRootPath());
        Path languageServerOutputDir = Paths.get(projectOptions.mem$dir).normalize();
        try 
        {
            Path outputDir = Files.createDirectories(languageServerOutputDir);
            System.setErr(new PrintStream(new FileOutputStream
            (
                outputDir.resolve(LOGFILE_NAME).toFile()
            )));
        } catch (IOException e) 
        {
            e.printStackTrace();
            throw new RuntimeException
            (
                couldNotCreateOutputDirMessage(languageServerOutputDir.toString()), e
            );
        }
        projectGlobal = CompileService.createCompileGlobal(projectOptions);
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Incremental);
		res.getCapabilities().setHoverProvider(true);
		return CompletableFuture.supplyAsync(() -> res);
	}

	@Override
	public CompletableFuture<Object> shutdown() 
    {
		return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
	}

	@Override
	public void exit() 
    {
		System.exit(0);
	}

	@Override
	public TextDocumentService getTextDocumentService() 
    {
		return this.textService;
	}

	@Override
	public WorkspaceService getWorkspaceService()
    {
		return this.workspaceService;
	}

	@Override
	public void connect(LanguageClient client)
    {
		this.client = client;
	}
}