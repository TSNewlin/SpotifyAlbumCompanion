package spotifyalbums;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class SpotifyAlbumSearcher {

    private static final SpotifyApi spotifyApi = new SpotifyApiInitializer().initializeApi();
    private final String albumName;

    public SpotifyAlbumSearcher(String albumName) {
        this.albumName = albumName;
    }

    public Album searchForAlbum() throws ParseException, SpotifyWebApiException, IOException {
        String albumId = searchForAlbumSimplifiedID();
        GetAlbumRequest albumRequest = spotifyApi.getAlbum(albumId).build();
        return albumRequest.execute();
    }

    private String searchForAlbumSimplifiedID() throws ParseException, SpotifyWebApiException, IOException {
        SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(albumName).limit(1).build();
        Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
        AlbumSimplified albumSimplified = albumSimplifiedPaging.getItems()[0];
        return albumSimplified.getId();
    }

}