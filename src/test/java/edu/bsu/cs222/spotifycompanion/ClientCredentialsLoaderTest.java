package edu.bsu.cs222.spotifycompanion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import edu.bsu.cs222.spotifycompanion.model.ClientCredentialsLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientCredentialsLoaderTest {

    @Test
    public void testLoadCredentialsFrom() throws IOException {
        ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
        List<String> result = credentialsLoader.loadCredentialsFrom("fakecredentials.txt");
        List<String> actual = Arrays.asList("fake_client_id", "fake_client_secret");
        Assertions.assertEquals(result, actual);
    }

}