package edu.bsu.cs222.spotifycompanion.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientCredentialsLoader {

    private BufferedReader reader;

    public List<String> loadCredentialsFrom(String fileName) throws IOException{
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        return parseLineToList();
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
