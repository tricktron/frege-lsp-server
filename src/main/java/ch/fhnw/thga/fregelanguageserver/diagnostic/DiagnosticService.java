package ch.fhnw.thga.fregelanguageserver.diagnostic;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;

import frege.compiler.types.Global.TGlobal;

public class DiagnosticService
{
    private static List<Diagnostic> getCompilerDiagnostics(TGlobal global)
    {
        return performUnsafe(DiagnosticLSP.getDiagnosticsLSP(global)).call();
    }

    public static void publishCompilerDiagnostics(
        LanguageClient client, 
        TGlobal global,
        String documentUri)
    {
		CompletableFuture.runAsync(() -> 
        {
            client.publishDiagnostics(new PublishDiagnosticsParams(
                documentUri, getCompilerDiagnostics(global)));
        });
	}

    public static void cleanCompilerDiagnostics
        (
            LanguageClient client,
            String documentUri
        )
    {
        CompletableFuture.runAsync(() -> 
        {
            client.publishDiagnostics(new PublishDiagnosticsParams(
                documentUri, Collections.emptyList()));
        });
    }
}
