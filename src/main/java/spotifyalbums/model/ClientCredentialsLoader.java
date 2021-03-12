package spotifyalbums.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientCredentialsLoader {

    private BufferedReader reader;

    public List<String> loadCredentialsFrom(String fileName) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        return parseLineToList();
    }

    private List<String> parseLineToList() {
        List<String> credentialsList = new ArrayList<>();
        try{
            String line;
            while ((line = reader.readLine()) != null) {
                credentialsList.add(line);
            }
        } catch (IOException ioException) {
            System.out.println("IOException:" + ioException.getMessage());
        }
        return credentialsList;
    }
}
