package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class SpotifyApiApplicant {

    private final SpotifyApi spotifyApi;

    public SpotifyApiApplicant() throws SpotifyWebApiException {
        this.spotifyApi = new SpotifyApiInitializer().initializeApi();
    }

    public Recommendations searchForRecommendations(Album album) throws SpotifyWebApiException{
        try {
            GetRecommendationsRequest recommendationsRequest = spotifyApi.getRecommendations().limit(10)
                    .seed_artists(getAllAlbumArtistsIds(album)).build();
            return recommendationsRequest.execute();
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            throw new SpotifyWebApiException();
        }
    }

    public Album searchForAlbum(String albumName) throws SpotifyWebApiException {
        try {
            String albumId = searchForAlbumSimplifiedID(albumName);
            GetAlbumRequest albumRequest = spotifyApi.getAlbum(albumId).build();
            return albumRequest.execute();
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            throw new SpotifyWebApiException();
        }
    }

    private String getAllAlbumArtistsIds(Album album) {
        StringBuilder artistsIds = new StringBuilder();
        for (ArtistSimplified a : album.getArtists()) {
            artistsIds.append(String.format("%s,", a.getId()));
        }
        return artistsIds.toString();
    }

    private String searchForAlbumSimplifiedID(String albumName) throws ParseException, SpotifyWebApiException,
            IOException {
        SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(albumName).limit(1).build();
        Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
        if (albumSimplifiedPaging.getTotal() == 0) {
            return "";
        }
        AlbumSimplified albumSimplified = albumSimplifiedPaging.getItems()[0];
        return albumSimplified.getId();
    }

}