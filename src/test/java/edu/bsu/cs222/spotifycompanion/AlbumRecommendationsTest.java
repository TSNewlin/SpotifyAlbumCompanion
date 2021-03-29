package edu.bsu.cs222.spotifycompanion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlbumRecommendationsTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"0 | How Fleeting, How Fragile",
    "1 | Automata I", "2 | Wanderer", "3 | From the Gallery of Sleep", "4 | Domino",
    "5 | Immortal", "6 | It Hates You", "7 | The Act", "8 | Sonder", "9 | Evergreen"})
    public void testAlbumRecommendationNames(int recommendationNumber, String testTitle) {
        AlbumRecommendations testRecommendations = createTestAlbumRecommendations();
        Assertions.assertEquals(testRecommendations.getAlbums()[recommendationNumber].getName(), testTitle);
    }

    private AlbumRecommendations createTestAlbumRecommendations() {
        InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testrecommendations.json");
        String jsonString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(jsonStream)))
                .lines().collect(Collectors.joining());
        JsonObject albumJsonObject = (JsonObject) JsonParser.parseString(jsonString);
        return new AlbumRecommendations.JsonUtil().createModelObject(albumJsonObject);
    }

}
