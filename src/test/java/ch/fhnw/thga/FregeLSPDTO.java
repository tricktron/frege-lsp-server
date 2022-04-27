package ch.fhnw.thga;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.eclipse.lsp4j.services.LanguageClient;

public final class FregeLSPDTO
{
    final FregeLanguageServer server;
    final LanguageClient client;

    public FregeLSPDTO()
    {
        server = spy(FregeLanguageServer.class);
        client = mock(LanguageClient.class);
        server.connect(client);
    }
    
    public FregeLanguageServer getServer()
    {
        return this.server;
    }

    public LanguageClient getClient()
    {
        return this.client;
    }
}
