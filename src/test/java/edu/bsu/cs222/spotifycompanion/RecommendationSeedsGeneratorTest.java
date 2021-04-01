package edu.bsu.cs222.spotifycompanion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.RecommendationSeedsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecommendationSeedsGeneratorTest {

    @Test
    public void testGenerateArtistSeeds() {
        Album testAlbum = createTestAlbum();
        RecommendationSeedsGenerator seedsGenerator = new RecommendationSeedsGenerator(testAlbum);
        Assertions.assertEquals("4MzJMcHQBl9SIYSjwWn8QW", seedsGenerator.generateArtistsSeed());
    }

    private Album createTestAlbum() {
        InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testalbum.json");
        String jsonString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(jsonStream)))
                .lines().collect(Collectors.joining());
        JsonObject albumJsonObject = (JsonObject) JsonParser.parseString(jsonString);
        return new Album.JsonUtil().createModelObject(albumJsonObject);
    }


}
