package spotifyalbums;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class SpotifyApiInitializer {

    public SpotifyApi initializeApi() {
        ClientCredentialsLoader credentialsLoader = new ClientCredentialsLoader();
        List<String> clientCredentialsList = credentialsLoader.loadCredentialsFrom("clientcredentials.txt");
        return initializeWithClientCredentialFlow(clientCredentialsList);
    }

    private SpotifyApi initializeWithClientCredentialFlow(List<String> clientCredentialsList) {
        SpotifyApi spotifyApi = SpotifyApi.builder().setClientId(clientCredentialsList.get(0))
                .setClientSecret(clientCredentialsList.get(1))
                .build();
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        try {
            ClientCredentials clientCredentials = credentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException | ParseException | SpotifyWebApiException exception) {
            System.out.println("Error:" + exception.getMessage());
        }
        return spotifyApi;
    }

}
