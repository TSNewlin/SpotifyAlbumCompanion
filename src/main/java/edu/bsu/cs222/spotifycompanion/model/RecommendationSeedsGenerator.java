package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;

import java.util.Objects;

public class RecommendationSeedsGenerator {

    private final ArtistSimplified[] artists;

    public RecommendationSeedsGenerator(Album album) {
        this.artists = Objects.requireNonNull(album).getArtists();
    }

    public String generateArtistsSeed() {
        StringBuilder artistsSeed = new StringBuilder();
        for (int i = 0; i < artists.length; i++) {
            artistsSeed.append(formatArtistIdAtIndex(i));
        }
        return artistsSeed.toString();
    }

    private String formatArtistIdAtIndex(int index) {
        if (index >= 1) {
            return "," + artists[index].getId();
        } else {
            return artists[index].getId();
        }
    }

}
