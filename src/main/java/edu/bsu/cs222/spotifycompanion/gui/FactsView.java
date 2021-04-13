package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import edu.bsu.cs222.spotifycompanion.model.AlbumDurationCalculator;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FactsView extends InformationView {

    public FactsView() {
        super();
        List<String> labelTextList = Arrays.asList("Title", "Artists", "Release Date", "Spotify Popularity",
                "Duration", "Record Label");
        for(int i = 0; i < labelTextList.size(); i++) {
            add(new Text(labelTextList.get(i)), 0, i);
        }
    }

    public void show(Album album) {
        getChildren().removeIf(node -> getColumnIndex(node) == 1);
        formatGrid(album);
    }

    private void formatGrid(Album album) {
        List<Text> informationList = getInformationList(album);
        for (int i = 0; i < informationList.size(); i++) {
            add(informationList.get(i), 1, i);
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
