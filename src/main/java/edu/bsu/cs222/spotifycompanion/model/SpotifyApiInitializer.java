package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class SpotifyApiInitializer {

    public SpotifyApi initializeApi() throws SpotifyWebApiException {
        try {
            ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
            List<String> clientCredentialsList = credentialsLoader.loadCredentialsFrom("clientcredentials.txt");
            return initializeWithClientCredentialFlow(clientCredentialsList);
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new SpotifyWebApiException();
        }
    }

    private SpotifyApi initializeWithClientCredentialFlow(List<String> clientCredentialsList)
            throws SpotifyWebApiException, IOException, ParseException {
        SpotifyApi spotifyApi = SpotifyApi.builder().setClientId(clientCredentialsList.get(0))
                .setClientSecret(clientCredentialsList.get(1))
                .build();
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        spotifyApi.setAccessToken(credentialsRequest.execute().getAccessToken());
        return spotifyApi;
    }

}
