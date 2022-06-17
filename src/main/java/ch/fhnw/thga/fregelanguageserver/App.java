package ch.fhnw.thga.fregelanguageserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

public class App 
{

    public static void startServer
        (
        InputStream in, 
        OutputStream out) 
        throws InterruptedException, ExecutionException 
    {
        FregeLanguageServer server        = new FregeLanguageServer();
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher
        (
            server,
            in, 
            out
        );
        server.connect(launcher.getRemoteProxy());
        launcher.startListening();
    }

    public static void main(String[] args) 
        throws InterruptedException, ExecutionException 
    {
        startServer(System.in, System.out);
    }
}
