package edu.bsu.cs222.spotifycompanion.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@JsonDeserialize(builder = AlbumRecommendations.Builder.class)
public class AlbumRecommendations extends AbstractModelObject {

    private final Track[] tracks;
    private final ArrayList<AlbumSimplified> recommendedAlbumList = new ArrayList<>();

    private AlbumRecommendations(final Builder builder) {
        super(builder);
        this.tracks = builder.tracks;
        getAlbumSimplifiedsFromTracks();
    }


    @Override
    public String toString() {
        return "tracks = " + Arrays.toString(tracks);
    }

    public ArrayList<String> getRecommendedAlbumNames() {
        ArrayList<String> recommendedAlbumNames = new ArrayList<>();
        for (AlbumSimplified album : recommendedAlbumList) {
            recommendedAlbumNames.add(album.getName());
        }
        return recommendedAlbumNames;
    }

    private void getAlbumSimplifiedsFromTracks() {
        for (Track track : tracks) {
            recommendedAlbumList.add(track.getAlbum());
        }

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
            return new AlbumRecommendations.Builder().setTracks(hasAndNotNull(jsonObject, "tracks") ?
                    new Track.JsonUtil().createModelObjectArray(jsonObject.getAsJsonArray("tracks")) : null).build();
        }
    }

}
