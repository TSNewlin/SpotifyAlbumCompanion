package edu.bsu.cs222.spotifycompanion.model;

import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.AbstractDataRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Objects;

public class AlbumRecommendationsRequest extends AbstractDataRequest<AlbumRecommendations> {

    private AlbumRecommendationsRequest(final Builder builder) {
        super(builder);
    }

    @Override
    public AlbumRecommendations execute() throws IOException, SpotifyWebApiException, ParseException {
        return new AlbumRecommendations.JsonUtil().createModelObject(getJson());
    }

    public static final class Builder extends AbstractDataRequest.Builder<AlbumRecommendations, Builder> {

        public Builder(String accessToken) {
            super(accessToken);
            this.setScheme("https");
            this.setPort(443);
            this.setHost("api.spotify.com");
            this.setHttpManager(new SpotifyHttpManager.Builder().build());
        }

        public Builder limit(Integer limit) {
            assert(limit >= 1 && limit <= 100);
            return setQueryParameter("limit", limit);
        }

        public Builder seed_artists(String seed_artists) {
            Objects.requireNonNull(seed_artists);
            assert(seed_artists.split(",").length <= 5);
            return setQueryParameter("seed_artists", seed_artists);
        }

        public Builder seed_tracks(String seed_tracks) {
            Objects.requireNonNull(seed_tracks);
            assert(seed_tracks.split(",").length <= 5);
            return setQueryParameter("seed_tracks", seed_tracks);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public AlbumRecommendationsRequest build() {
            setPath("/v1/recommendations");
            return new AlbumRecommendationsRequest(this);
        }
    }

}
