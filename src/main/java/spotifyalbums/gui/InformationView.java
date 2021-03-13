package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import spotifyalbums.model.AlbumDurationCalculator;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class InformationView extends GridPane {

    public InformationView() {
        initializeGrid();
    }

    public void show(Album album) {
        this.getChildren().clear();
        initializeGrid();
        formatGrid(album);
    }

    private void initializeGrid() {
        this.setPrefHeight(300);
        this.setVgap(10);
        this.setHgap(20);
        this.add(new Text("Title"), 0, 0);
        this.add(new Text("Artist"), 0, 1);
        this.add(new Text("Album Release Date"), 0, 2);
        this.add(new Text("Popularity"), 0, 3);
        this.add(new Text("Album Duration"), 0, 4);
        this.add(new Text("Album Label"), 0, 5);
    }

    public void formatGrid(Album album) {
        List<Text> informationList = getInformationList(album);
        for (int i = 0; i < informationList.size(); i++) {
            this.add(informationList.get(i), 1, i);
        }
    }

    private List<Text> getInformationList(Album album) {
        AlbumDurationCalculator durationCalculator = new AlbumDurationCalculator();
        Duration albumDuration = durationCalculator.calculateAlbumDuration(album);
        ArtistSimplified artistSimplified = album.getArtists()[0];
        return Arrays.asList(new Text(album.getName()),
                new Text(artistSimplified.getName()),
                new Text(album.getReleaseDate()),
                new Text(album.getPopularity().toString() + " / 100"),
                new Text(String.format("%d minutes", albumDuration.toMinutes())),
                new Text(album.getLabel()));
    }

}
