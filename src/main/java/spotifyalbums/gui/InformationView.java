package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class InformationView extends VBox {

    private final TextArea resultArea = new TextArea();

    public InformationView() {
        resultArea.setPrefRowCount(31);
        getChildren().add(resultArea);
    }

    public void show(Album album) {
        resultArea.clear();
        StringBuilder builder = new StringBuilder();
        for (String i : getInformationList(album)) {
            builder.append(i);
        }
        String albumInformation = builder.toString();
        resultArea.setText(albumInformation);
    }

    public List<String> getInformationList(Album album) {
        ArtistSimplified artistSimplified = album.getArtists()[0];
        String artist = "Artist: " + artistSimplified.getName() + "\n" + "\n";
        String releaseDate = "Album release date: " + album.getReleaseDate() + "\n" + "\n";
        String popularity = "Popularity: " + album.getPopularity().toString() + "\n" + "\n";
        String albumLabel = "Album label: " + album.getLabel() + "\n" + "\n";
        return Arrays.asList(artist, releaseDate, popularity, albumLabel);
    }

}
