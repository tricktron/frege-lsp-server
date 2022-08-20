package ch.fhnw.thga.fregelanguageserver;

import static ch.fhnw.thga.fregelanguageserver.compile.CompileService.ROOT_OUTPUT_DIR;
import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
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

import ch.fhnw.thga.fregelanguageserver.compile.CompileService;
import ch.fhnw.thga.fregelanguageserver.project.DefaultProject;
import ch.fhnw.thga.fregelanguageserver.project.GradleProjectOptions;
import frege.compiler.types.Global.TGlobal;
import frege.compiler.types.Global.TOptions;


public class FregeLanguageServer implements LanguageServer, LanguageClientAware 
{
    private static final String LOGFILE_NAME = "frege.log";
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

    private TOptions createProjectOptions(Path projectRootPath)
    {
        if (projectRootPath.resolve("build.gradle").toFile().exists())
        {
            return new GradleProjectOptions()
            .getCompileOptions(projectRootPath.toFile().getAbsolutePath());
        }
        return new DefaultProject().getCompileOptions();
    }

    private String couldNotCreateOutputDirMessage(String outputDirPath)
    {
        return String.format
        (
            "Could not create output dir %s. Please create it manually and restart the server.",
            outputDirPath
        );
    }

    private void createLogFile(Path projectRootPath)
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

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params)
    {
        Path projectRootPath = Path.of
        (
            URI.create(params.getWorkspaceFolders().get(0).getUri())
        );
        createLogFile(projectRootPath);
        TOptions projectOptions = createProjectOptions(projectRootPath);
        Path languageServerOutputDir = Paths.get(projectOptions.mem$dir).normalize();
        try 
        {
            Files.createDirectories(languageServerOutputDir);
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
        performUnsafe(frege.control.Concurrent.shutdown.call());
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