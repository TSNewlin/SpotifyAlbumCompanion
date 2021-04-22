package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
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
        Paging<TrackSimplified> tracksPaging = album.getTracks();
        int counter = 0;
        for (TrackSimplified track : tracksPaging.getItems()) {
            add(new Text("Track #" + track.getTrackNumber()), 0, counter );
            add(ActionSetHyperLink.withText(track.getName()).andExternalUrl(track.getExternalUrls().get("spotify")).andUri(track.getUri()), 1, counter);
            counter++;
        }

    }

}
