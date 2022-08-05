package ch.fhnw.thga.fregelanguageserver.hover;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

import frege.compiler.types.Global.TGlobal;
import frege.prelude.Maybe;
import frege.prelude.PreludeBase.TMaybe;
import frege.run8.Thunk;

public class HoverService
{

    public static CompletableFuture<Hover> hover
        (
            HoverParams params, 
            TGlobal global
        )
    {
        return CompletableFutures.computeAsync(cancel ->
        {
            if (global == null || cancel.isCanceled())
            {
                return null;
            }
            
            TMaybe<Hover> hover = HoverLSP.getTypeSignatureOnHoverLSP
            ( 
                Thunk.lazy(params.getPosition()),
                global
            ).call();

            if (Maybe.isNothing(hover)) return null;
            return hover.asJust().mem1.call();
        });
    }
}