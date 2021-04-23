package edu.bsu.cs222.spotifycompanion.gui;

import com.google.common.collect.ImmutableList;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.AlbumDurationCalculator;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.List;

public class FactsView extends InformationView {

    public FactsView() {
        super();
        ImmutableList<String> labelTextList = ImmutableList.of("Release Date:", "Spotify Popularity:",
                "Duration:", "Record Label:");
        for (int i = 0; i < labelTextList.size(); i++) {
            Text informationLabelText = new Text(labelTextList.get(i));
            informationLabelText.setStyle("-fx-fill: white; -fx-font-size: 14; -fx-font-weight: bold; " +
                    "-fx-underline: true;");
            add(informationLabelText, 0, i);
        }
    }

    public void show(Album album) {
        getChildren().removeIf(node -> getColumnIndex(node) == 1);
        formatGrid(album);
    }

    private void formatGrid(Album album) {
        List<Text> informationList = makeInformationList(album);
        for (int i = 0; i < informationList.size(); i++) {
            add(informationList.get(i), 1, i);
        }
    }

    private List<Text> makeInformationList(Album album) {
        AlbumDurationCalculator durationCalculator = new AlbumDurationCalculator();
        Duration albumDuration = durationCalculator.calculateAlbumDuration(album);
        List<Text> informationTextList = ImmutableList.of(
                new Text(album.getReleaseDate()),
                new Text(album.getPopularity().toString() + " / 100"),
                new Text(String.format("%d minutes", albumDuration.toMinutes())),
                new Text(album.getLabel())
        );
        for(Text t: informationTextList) { t.setStyle("-fx-fill: white; -fx-font-size: 14; -fx-font-weight: bold;"); }
        return informationTextList;
    }

}
