package spotifyalbums.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class SpotifyApiInitializer {

    public SpotifyApi initializeApi() throws SpotifyWebApiException {
        ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
        List<String> clientCredentialsList = credentialsLoader.loadCredentialsFrom("clientcredentials.txt");
        return initializeWithClientCredentialFlow(clientCredentialsList);
    }

    private SpotifyApi initializeWithClientCredentialFlow(List<String> clientCredentialsList) throws SpotifyWebApiException {
        SpotifyApi spotifyApi = SpotifyApi.builder().setClientId(clientCredentialsList.get(0))
                .setClientSecret(clientCredentialsList.get(1))
                .build();
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        spotifyApi.setAccessToken(executeClientCredentialsRequest(credentialsRequest).getAccessToken());
        return spotifyApi;
    }

    private ClientCredentials executeClientCredentialsRequest(ClientCredentialsRequest request) throws SpotifyWebApiException {
        try {
            return request.execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new SpotifyWebApiException();
        }
    }

}
