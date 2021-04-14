package edu.bsu.cs222.spotifycompanion;

import edu.bsu.cs222.spotifycompanion.model.ClientCredentialsLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.prefs.Preferences;

public class ClientCredentialsLoaderTest {

    @ParameterizedTest
    @CsvSource({"userId, fake_client_id", "userSecret, fake_client_secret"})
    public void testCredentialsLoader_getStatements(String testPreference, String expectedPreference) throws IOException {
        ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
        Preferences testPreferences = credentialsLoader.loadCredentialsFrom("fakecredentials.txt");
        Assertions.assertEquals(expectedPreference, testPreferences.get(testPreference, "invalid"));
    }

}