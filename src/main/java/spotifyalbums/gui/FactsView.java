package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import javafx.scene.text.Text;
import spotifyalbums.model.AlbumDurationCalculator;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FactsView extends InformationView {

    public FactsView() {
        super();
        add(new Text("Title"), 0, 0);
        add(new Text("Artist"), 0, 1);
        add(new Text("Release Date"), 0, 2);
        add(new Text("Spotify Popularity"), 0, 3);
        add(new Text("Duration"), 0, 4);
        add(new Text("Record Label"), 0, 5);
    }

    public void show(Album album) {
        getChildren().removeIf(node -> getColumnIndex(node) == 1);
        formatGrid(album);
    }

    public void formatGrid(Album album) {
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
