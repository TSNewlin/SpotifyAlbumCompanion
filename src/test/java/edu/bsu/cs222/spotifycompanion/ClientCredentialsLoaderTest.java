package edu.bsu.cs222.spotifycompanion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import edu.bsu.cs222.spotifycompanion.model.ClientCredentialsLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

public class ClientCredentialsLoaderTest {

    private final Preferences testUserPreferences = Preferences.userNodeForPackage(this.getClass());
    private final ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();

    @Test
    public void testLoadCredentialsFrom_UserID() throws IOException {
        String expectedUserID = getTestUserID();
        Preferences actualPreferences = credentialsLoader.loadCredentialsFrom("fakecredentials.txt");
        String actualUserID = actualPreferences.get("userID", "root");
        Assertions.assertEquals(expectedUserID, actualUserID);
    }

    @Test
    public void testLoadCredentialsFrom_UserSecret() throws IOException {
        String expectedUserSecret = getTestUserSecret();
        Preferences actualPreferences = credentialsLoader.loadCredentialsFrom("fakecredentials.txt");
        String actualUserSecret = actualPreferences.get("userSecret", "root");
        Assertions.assertEquals(expectedUserSecret, actualUserSecret);
    }

    private String getTestUserID() {
        testUserPreferences.put("userID", "kG7jAee21k9hNdGVOIxcgruRMso6zKifD");
        return testUserPreferences.get("userID", "root");
    }

    private String getTestUserSecret() {
        testUserPreferences.put("userSecret", "FAHrrlZ8BVGH2ralukF8QwymnP7t4BBJ3");
        return testUserPreferences.get("userSecret", "root");
    }

}