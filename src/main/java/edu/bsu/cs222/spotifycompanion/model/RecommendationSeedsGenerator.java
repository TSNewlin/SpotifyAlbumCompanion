package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;

public class RecommendationSeedsGenerator {

    private final ArtistSimplified[] artists;

    public RecommendationSeedsGenerator(Album album) {
        this.artists = album.getArtists();
    }

    public String generateArtistsSeed() {
        StringBuilder artistsSeed = new StringBuilder();
        for (ArtistSimplified artist : artists) {
            artistsSeed.append(artist.getId());
        }
        return artistsSeed.toString();
    }

}
