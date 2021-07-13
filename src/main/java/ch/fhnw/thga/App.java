package ch.fhnw.thga;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

public class App {

    public static void startServer(InputStream in, OutputStream out) throws InterruptedException, ExecutionException {
        SimpleLanguageServer server = new SimpleLanguageServer();
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, in, out);
        server.connectClient(launcher.getRemoteProxy());
        Future<?> startListening = launcher.startListening();
        startListening.get();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        startServer(System.in, System.out);
    }
}
