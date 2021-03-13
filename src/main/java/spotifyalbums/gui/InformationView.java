package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import spotifyalbums.model.AlbumDurationCalculator;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InformationView extends GridPane {

    private final GridPane resultArea = new GridPane();

    public InformationView() {
        getChildren().add(resultArea);
        initializeGrid();
    }

    public void show(Album album) {
        resultArea.getChildren().clear();
        initializeGrid();
        formatGrid(album);
    }

    private List<Text> getInformationList(Album album) {
        AlbumDurationCalculator durationCalculator = new AlbumDurationCalculator();
        Duration albumDuration = durationCalculator.calculateAlbumDuration(album);
        ArtistSimplified artistSimplified = album.getArtists()[0];
        Text artist = new Text(artistSimplified.getName());
        Text releaseDate = new Text(album.getReleaseDate());
        Text popularity = new Text(album.getPopularity().toString() + " / 100");
        Text duration = new Text(String.format("%d" + " minutes", albumDuration.toMinutes()));
        Text albumLabel = new Text(album.getLabel());
        return Arrays.asList(artist, releaseDate, popularity, duration, albumLabel);
    }

    public void formatGrid(Album album) {
        List<Text> informationList = getInformationList(album);
        for (int i = 0; i < informationList.size(); i++) {
            resultArea.add(informationList.get(i), 1, i);
        }
    }

    public void initializeGrid() {
        resultArea.setPrefHeight(300);
        resultArea.setVgap(10);
        resultArea.setHgap(20);
        Text artistLbl = new Text("Artist");
        Text albumReleaseDateLbl = new Text("Album Release Date");
        Text popularityLbl = new Text("Popularity");
        Text durationLbl = new Text("Album Duration");
        Text albumLabelLbl = new Text("Album Label");
        resultArea.add(artistLbl, 0, 0);
        resultArea.add(albumReleaseDateLbl, 0, 1);
        resultArea.add(popularityLbl, 0, 2);
        resultArea.add(durationLbl, 0, 3);
        resultArea.add(albumLabelLbl, 0, 4);
    }

}
