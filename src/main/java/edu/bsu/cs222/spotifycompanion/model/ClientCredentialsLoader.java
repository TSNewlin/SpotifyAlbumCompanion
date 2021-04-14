package edu.bsu.cs222.spotifycompanion.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.prefs.Preferences;

public class ClientCredentialsLoader {

    private BufferedReader reader;
    private final Preferences userPreferences = Preferences.userNodeForPackage(this.getClass());

    public Preferences loadCredentialsFrom(String fileName) throws IOException{
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        return getUserPreferences(parseLineToList());
    }

    private Preferences getUserPreferences(List<String> credentials) {
        userPreferences.put("userId", credentials.get(0));
        userPreferences.put("userSecret", credentials.get(1));
        return userPreferences;
    }

    private List<String> parseLineToList() throws IOException {
        List<String> credentialsList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            credentialsList.add(line);
        }
        return credentialsList;
    }

}
