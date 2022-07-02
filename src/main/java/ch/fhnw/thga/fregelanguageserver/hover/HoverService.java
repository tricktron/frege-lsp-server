package ch.fhnw.thga.fregelanguageserver.hover;

import static frege.prelude.PreludeBase.TST.performUnsafe;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

import frege.prelude.Maybe;
import frege.prelude.PreludeBase.TMaybe;
import frege.run8.Thunk;

public class HoverService
{

    public static CompletableFuture<Hover> hover(HoverParams params, String fregeCode)
    {
        return CompletableFutures.computeAsync(cancel ->
        {
            if (cancel.isCanceled())
            {
                return null;
            }
            
            TMaybe<MarkupContent> content = performUnsafe(
                HoverLSP.compileAndGetTypeSignatureOnHoverLSP(
                    Thunk.lazy(fregeCode), 
                    Thunk.lazy(params.getPosition())).call()).call();
            if (Maybe.isNothing(content)) return null;
            return new Hover(content.asJust().mem1.call());
        });
    }
}