package edu.bsu.cs222.spotifycompanion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import edu.bsu.cs222.spotifycompanion.model.ClientCredentialsLoader;

import java.util.Arrays;
import java.util.List;

public class ClientCredentialsLoaderTest {

    @Test
    public void testLoadCredentialsFrom() {
        ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
        List<String> result = credentialsLoader.loadCredentialsFrom("fakecredentials.txt");
        List<String> actual = Arrays.asList("kG7jAee21k9hNdGVOIxcgruRMso6zKifD", "FAHrrlZ8BVGH2ralukF8QwymnP7t4BBJ3");
        Assertions.assertEquals(result, actual);
    }

}