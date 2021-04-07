package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

public class RecommendationSeedsGenerator {

    private final int SEEDS_LIMIT = 5;
    private Album album;

    public String generateArtistsSeed(Album album) {
        StringBuilder artistsSeed = new StringBuilder();
        for (int i = 0; i < album.getArtists().length; i++) {
            if (i > 0) {
                artistsSeed.append(',');
            }
            artistsSeed.append(album.getArtists()[i].getId());
        }
        return artistsSeed.toString();
    }

    public String generateTracksSeed(Album album) {
        this.album = album;
        if (!totalTracksAndArtistsExceedsSeedLimit()) {
            return formatTrackIdsIntoTrackSeed(album.getTracks().getTotal());
        } else {
            return formatTrackIdsIntoTrackSeed(SEEDS_LIMIT - album.getArtists().length);
        }
    }

    private Boolean totalTracksAndArtistsExceedsSeedLimit() {
        return album.getArtists().length + album.getTracks().getTotal() > SEEDS_LIMIT;
    }

    private String formatTrackIdsIntoTrackSeed(int totalAllowedTrackIds) {
        StringBuilder tracksSeed = new StringBuilder();
        tracksSeed.append(album.getTracks().getItems()[0].getId());
        for (int i = 1; i < totalAllowedTrackIds; i++) {
            tracksSeed.append(formatNonFirstTrackId(album.getTracks().getItems()[i]));
        }
        return tracksSeed.toString();
    }

    private String formatNonFirstTrackId(TrackSimplified track) {
        return "," + track.getId();
    }

}
