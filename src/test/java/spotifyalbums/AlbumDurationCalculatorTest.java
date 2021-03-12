package spotifyalbums;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wrapper.spotify.model_objects.specification.Album;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spotifyalbums.model.AlbumDurationCalculator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlbumDurationCalculatorTest {

    @Test
    public void testCalculateAlbumDuration() {
        Album testAlbum = createTestAlbum();
        AlbumDurationCalculator durationCalculator = new AlbumDurationCalculator();
        Duration testDuration = Duration.ofMillis(1430277);
        Assertions.assertEquals(testDuration, durationCalculator.calculateAlbumDuration(testAlbum));
    }

    private Album createTestAlbum() {
        InputStream jsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testalbum.json");
        String jsonString = new BufferedReader(new InputStreamReader(Objects.requireNonNull(jsonStream)))
                            .lines().collect(Collectors.joining());
        JsonObject albumJsonObject = (JsonObject) JsonParser.parseString(jsonString);
        return new Album.JsonUtil().createModelObject(albumJsonObject);
    }

}
