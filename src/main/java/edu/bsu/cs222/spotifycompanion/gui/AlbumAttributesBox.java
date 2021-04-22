package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AlbumAttributesBox extends VBox {

    public void show(Album album) {
        getChildren().clear();
        Image albumCover = album.getImages()[1];
        ImageView albumCoverImageView = new ImageView(albumCover.getUrl());
        ActionSetHyperLink albumHyperLink = ActionSetHyperLink.withText(album.getName())
                .andExternalUrl(album.getExternalUrls().get("spotify")).andUri(album.getUri());
        ActionSetHyperLink artistHyperLink = ActionSetHyperLink.withText(album.getArtists()[0].getName())
                .andExternalUrl(album.getArtists()[0].getExternalUrls().get("spotify"))
                .andUri(album.getArtists()[0].getUri());
        getChildren().addAll(albumCoverImageView, albumHyperLink, artistHyperLink);
    }

}
