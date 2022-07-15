package ch.fhnw.thga.fregelanguageserver.diagnostic;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;

import frege.run8.Thunk;

public class DiagnosticService
{
    private static List<Diagnostic> getCompilerDiagnostics(String fregeCode)
    {
        return performUnsafe(
            DiagnosticLSP.compileAndGetDiagnosticsLSP(Thunk.lazy(fregeCode))).call();
    }

    public static void publishCompilerDiagnostics(
        LanguageClient client, 
        String fregeCode,
        String documentUri)
    {
		CompletableFuture.runAsync(() -> 
        {
            client.publishDiagnostics(new PublishDiagnosticsParams(
                documentUri, getCompilerDiagnostics(fregeCode)));
        });
	}
}
