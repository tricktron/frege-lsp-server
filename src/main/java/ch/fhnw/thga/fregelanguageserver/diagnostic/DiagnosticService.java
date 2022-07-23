package ch.fhnw.thga.fregelanguageserver.diagnostic;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;

import frege.compiler.types.Global.TGlobal;
import frege.run8.Thunk;
import frege.run8.Func.U;
import frege.runtime.Phantom.RealWorld;

public class DiagnosticService
{
    private static List<Diagnostic> getCompilerDiagnostics(U<RealWorld, TGlobal> global)
    {
        return performUnsafe(DiagnosticLSP.getDiagnosticsLSP(global)).call();
    }

    public static void publishCompilerDiagnostics(
        LanguageClient client, 
        U<RealWorld, TGlobal> global,
        String documentUri)
    {
		CompletableFuture.runAsync(() -> 
        {
            client.publishDiagnostics(new PublishDiagnosticsParams(
                documentUri, getCompilerDiagnostics(global)));
        });
	}
}
