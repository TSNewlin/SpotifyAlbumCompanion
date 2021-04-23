package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TracksView extends InformationView {

    public TracksView() {
        super();
    }

    public void show(Album album) {
        getChildren().clear();
        formatGrid(album);
    }

    private void formatGrid(Album album) {
        for (int i = 0; i < album.getTracks().getItems().length; i++) {
            Text trackNumber = new Text(String.format("Track #%d:", album.getTracks().getItems()[i].getTrackNumber()));
            trackNumber.setStyle("-fx-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");
            add(trackNumber, 0, i);
            add(createTrackHyperLink(album.getTracks().getItems()[i]), 1, i);
        }

    }

    private ActionSetHyperLink createTrackHyperLink(TrackSimplified track) {
        ActionSetHyperLink trackHyperLink = ActionSetHyperLink.withText(track.getName())
                .andExternalUrl(track.getExternalUrls().get("spotify")).andUri(track.getUri());
        trackHyperLink.setFont(Font.font(14));
        return trackHyperLink;
    }

}
