package edu.bsu.cs222.spotifycompanion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.RecommendationSeedsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecommendationSeedsGeneratorTest {

    private final RecommendationSeedsGenerator testSeedGenerator = new RecommendationSeedsGenerator();

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"4MzJMcHQBl9SIYSjwWn8QW | testAlbum.json",
    "3nFkdlSjzX9mRTtwJOzDYB,5K4W6rqBFWDnAN6FQUkS6x | testalbum_multiartist.json"})
    public void testGenerateArtistSeed(String artistsSeed, String fileName) {
        Album testAlbum = createTestAlbum(fileName);
        Assertions.assertEquals(artistsSeed, testSeedGenerator.generateArtistsSeed(testAlbum));
    }


    @Test
    public void testGenerateTracksSeed() {
        Album testAlbum = createTestAlbum("testalbum.json");
        Assertions.assertEquals("73qU8HnehMJsZoNQo6Y05o,5cmOlYycTDBTY8k8nHfrLf,7n0tr9KRPVDQ4Bo7ah8K45,5wCNFck4zVosUPpn7tcjZE",
                testSeedGenerator.generateTracksSeed(testAlbum));
    }

    private Album createTestAlbum(String fileName) {
        InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        String jsonString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(jsonStream)))
                .lines().collect(Collectors.joining());
        JsonObject albumJsonObject = (JsonObject) JsonParser.parseString(jsonString);
        return new Album.JsonUtil().createModelObject(albumJsonObject);
    }


}
