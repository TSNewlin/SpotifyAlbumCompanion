package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

public class RecommendationSeedsGenerator {

    private static final int SEEDS_LIMIT = 5;
    private ArtistSimplified[] artists;
    private Paging<TrackSimplified> tracks;

    public String generateArtistsSeed(Album album) {
        this.artists = album.getArtists();
        StringBuilder artistsSeed = new StringBuilder();
        for (int i = 0; i < artists.length; i++) {
            if (i > 0) {
                artistsSeed.append(',');
            }
            artistsSeed.append(album.getArtists()[i].getId());
        }
        return artistsSeed.toString();
    }

    public String generateTracksSeed(Album album) {
        this.artists = album.getArtists();
        this.tracks = album.getTracks();
        if (sumOfAllArtistsAndAllTracksExceedsSeedLimit()) {
            return generateTracksSeedWithTotalAllowedTracksIds(SEEDS_LIMIT - artists.length);
        }
        return generateTracksSeedWithTotalAllowedTracksIds(tracks.getTotal());
    }

    private Boolean sumOfAllArtistsAndAllTracksExceedsSeedLimit() {
        return artists.length + tracks.getTotal() > SEEDS_LIMIT;
    }

    private String generateTracksSeedWithTotalAllowedTracksIds(int totalAllowedTrackIds) {
        TrackSimplified[] tracksArray = tracks.getItems();
        StringBuilder tracksSeed = new StringBuilder(tracksArray[0].getId());
        for (int i = 1; i < totalAllowedTrackIds; i++) {
            tracksSeed.append(formatNonFirstTrackId(tracksArray[i]));
        }
        return tracksSeed.toString();
    }

    private String formatNonFirstTrackId(TrackSimplified track) {
        return "," + track.getId();
    }

}
