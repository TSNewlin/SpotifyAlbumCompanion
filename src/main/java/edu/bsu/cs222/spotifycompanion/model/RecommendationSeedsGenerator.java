package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;

public class RecommendationSeedsGenerator {

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
        StringBuilder tracksSeed = new StringBuilder();
        for (int i = 0; i < album.getTracks().getTotal() - album.getArtists().length; i++) {
            if (i > 0) {
                tracksSeed.append(',');
            }
            tracksSeed.append(album.getTracks().getItems()[i].getId());
        }
        return tracksSeed.toString();
    }


}
