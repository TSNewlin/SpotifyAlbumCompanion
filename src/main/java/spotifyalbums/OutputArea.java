package spotifyalbums;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class OutputArea extends VBox {

    private final TextArea textArea;

    public OutputArea() {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefRowCount(31);
        getChildren().add(textArea);
    }

    public void showAlbumInformationOf(String albumTitle) throws ParseException, SpotifyWebApiException, IOException {
        SpotifyAlbumSearcher spotifyAlbumSearcher = new SpotifyAlbumSearcher(albumTitle);
        AlbumTrackListingFormatter albumTrackListingFormatter = new AlbumTrackListingFormatter();
        String albumTracks = albumTrackListingFormatter.formatTrackListing(spotifyAlbumSearcher.searchForAlbum());
        textArea.clear();
        textArea.setText(albumTracks);
    }
}
