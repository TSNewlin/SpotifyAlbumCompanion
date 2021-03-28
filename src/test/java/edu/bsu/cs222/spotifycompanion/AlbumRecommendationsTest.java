package edu.bsu.cs222.spotifycompanion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlbumRecommendationsTest {

    @Test
    public void testGetTracks() {
        AlbumRecommendations testRecommendations = createTestAlbumRecommendations();
        Assertions.assertEquals(testRecommendations.getTracks().length, 10);
    }

    private AlbumRecommendations createTestAlbumRecommendations() {
        InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testrecommendations.json");
        String jsonString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(jsonStream)))
                .lines().collect(Collectors.joining());
        JsonObject albumJsonObject = (JsonObject) JsonParser.parseString(jsonString);
        return new AlbumRecommendations.JsonUtil().createModelObject(albumJsonObject);
    }

}
