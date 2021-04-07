package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

public class RecommendationSeedsGenerator {

    private final int SEEDS_LIMIT = 5;
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
        return totalTracksAndArtistsExceedsSeedLimit() ?
                createTracksSeedWithTotalAllowedIds(SEEDS_LIMIT - album.getArtists().length) :
                createTracksSeedWithTotalAllowedIds(album.getTracks().getTotal());
    }

    private Boolean totalTracksAndArtistsExceedsSeedLimit() {
        return artists.length + tracks.getTotal() > SEEDS_LIMIT;
    }

    private String createTracksSeedWithTotalAllowedIds(int totalAllowedTrackIds) {
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
