package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import javafx.scene.text.Text;

public class TracksView extends InformationView{

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
            this.add(new Text("Track #" + track.getTrackNumber()), 0, counter );
            this.add(new Text(track.getName()), 1, counter);
            counter++;
        }
    }

}
