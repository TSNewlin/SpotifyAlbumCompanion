package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
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
            trackNumber.setStyle("-fx-fill: #d204ba;");
            add(trackNumber, 0, i);
            add(ActionSetHyperLink.withText(album.getTracks().getItems()[i].getName())
                    .andExternalUrl(album.getTracks().getItems()[i].getExternalUrls().get("spotify"))
                    .andUri(album.getTracks().getItems()[i].getUri()), 1, i);
        }

    }

}
