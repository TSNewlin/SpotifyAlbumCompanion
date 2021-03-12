package spotifyalbums.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

import java.time.Duration;

public class AlbumDurationCalculator {

    public Duration calculateAlbumDuration(Album album) {
        Paging<TrackSimplified> trackList = album.getTracks();
        long trackDurationTotal = 0;
        for (TrackSimplified track : trackList.getItems()) {
            trackDurationTotal += track.getDurationMs();
        }
        return Duration.ofMillis(trackDurationTotal);
    }

}