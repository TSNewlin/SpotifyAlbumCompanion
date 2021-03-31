package edu.bsu.cs222.spotifycompanion;

import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendationsRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlbumRecommendationsRequestTest {

    @Test
    public void testAlbumRecommendationRequestBuilder_createsProperQueryUri() {
        String fakeAccessToken = "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk";
        AlbumRecommendationsRequest albumRecommendationsRequest = new AlbumRecommendationsRequest.Builder(fakeAccessToken)
                .limit(10).seed_artists("4MzJMcHQBl9SIYSjwWn8QW").seed_tracks("73qU8HnehMJsZoNQo6Y05o").build();
        Assertions.assertEquals("https://api.spotify.com:443/v1/recommendations?limit=10&seed_artists=4MzJMcHQBl9SIYSjwWn8QW&seed_tracks=73qU8HnehMJsZoNQo6Y05o",
                albumRecommendationsRequest.getUri().toString());
    }
}
