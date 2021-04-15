package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.prefs.Preferences;

public class SpotifyApiInitializer {

    public SpotifyApi initializeApi() throws SpotifyWebApiException {
        try {
            ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
            Preferences clientPreferences = credentialsLoader.loadCredentialsFrom("clientcredentials.txt");
            return initializeWithClientCredentialFlow(clientPreferences);
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new SpotifyWebApiException();
        }
    }

    private SpotifyApi initializeWithClientCredentialFlow(Preferences clientCredentials)
            throws SpotifyWebApiException, IOException, ParseException {
        SpotifyApi spotifyApi = SpotifyApi.builder().setClientId(clientCredentials.get("userID", "invalidID"))
                .setClientSecret(clientCredentials.get("userSecret", "invalidSecret"))
                .build();
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        spotifyApi.setAccessToken(credentialsRequest.execute().getAccessToken());
        return spotifyApi;
    }

}