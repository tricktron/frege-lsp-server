package ch.fhnw.thga.fregelanguageserver.hover;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;

public class HoverService
{

    public static CompletableFuture<Hover> hover(HoverParams params)
    {
        return CompletableFutures.computeAsync(cancel ->
        {
            if (cancel.isCanceled())
            {
                return null;
            }
            return LSPHover.getTypeSignature(params.getPosition());
        });
    }
}