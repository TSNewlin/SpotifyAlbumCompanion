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
        this.add(new Text("Title"), 0, 0);
    }

    public void show(Album album) {
        this.getChildren().removeIf(node -> getColumnIndex(node) == 1);
        formatGrid(album);
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
