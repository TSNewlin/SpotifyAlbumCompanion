package edu.bsu.cs222.spotifycompanion.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.Objects;

@JsonDeserialize(builder = Recommendations.Builder.class)
public class AlbumRecommendations extends AbstractModelObject {

    private final Track[] tracks;

    private AlbumRecommendations(final Builder builder) {
        super(builder);

        this.tracks = builder.tracks;
    }


    @Override
    public String toString() {
        return "tracks = " + Arrays.toString(tracks);
    }

    public Track[] getTracks() {
        return tracks;
    }

    @Override
    public Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractModelObject.Builder {
        private Track[] tracks;

        public Builder setTracks(Track... tracks) {
            this.tracks = tracks;
            return this;
        }

        @Override
        public AlbumRecommendations build() {
            return new AlbumRecommendations(this);
        }
    }

    public static final class JsonUtil extends AbstractModelObject.JsonUtil<AlbumRecommendations> {

        @Override
        public AlbumRecommendations createModelObject(JsonObject jsonObject) {
            Objects.requireNonNull(jsonObject);
            return new AlbumRecommendations.Builder().setTracks(
                    hasAndNotNull(jsonObject, "tracks")
                    ? new Track.JsonUtil().createModelObjectArray(
                            jsonObject.getAsJsonArray("tracks")
                    ) : null)
                    .build();
        }
    }

}
