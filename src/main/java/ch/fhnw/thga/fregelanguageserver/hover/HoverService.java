package ch.fhnw.thga.fregelanguageserver.hover;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

import frege.compiler.types.Global.TGlobal;
import frege.prelude.Maybe;
import frege.prelude.PreludeBase.TMaybe;
import frege.run8.Thunk;
import frege.run8.Func.U;
import frege.runtime.Phantom.RealWorld;

public class HoverService
{

    public static CompletableFuture<Hover> hover
        (
            HoverParams params, 
            U<RealWorld, TGlobal> global
        )
    {
        return CompletableFutures.computeAsync(cancel ->
        {
            if (cancel.isCanceled())
            {
                return null;
            }
            
            TMaybe<Hover> hover = performUnsafe(
                HoverLSP.getTypeSignatureOnHoverLSP(
                    global, 
                    Thunk.lazy(params.getPosition())).call()).call();
            if (Maybe.isNothing(hover)) return null;
            return hover.asJust().mem1.call();
        });
    }
}