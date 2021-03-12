package spotifyalbums.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

public class AlbumTrackListingFormatter {

    private final StringBuilder trackListing = new StringBuilder();

    public String formatTrackListing(Album album) {
        Paging<TrackSimplified> tracksPaging = album.getTracks();
        for (TrackSimplified track : tracksPaging.getItems()) {
            appendTrackToListing(track);
        }
        return trackListing.toString();
    }

    private void appendTrackToListing(TrackSimplified track) {
        String formattedSingleTrack = "Track " + track.getTrackNumber() + ": " + track.getName() + "\n";
        trackListing.append(formattedSingleTrack);
    }

}
