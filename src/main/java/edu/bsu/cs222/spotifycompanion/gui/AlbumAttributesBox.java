package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AlbumAttributesBox extends VBox {

    public AlbumAttributesBox() {
        setPadding(new Insets(5, 0, 0, 0));
        setPrefHeight(175);
        setAlignment(Pos.TOP_CENTER);
    }

    public void show(Album album) {
        getChildren().clear();
        ImageView albumCoverImageView = new ImageView(album.getImages()[1].getUrl());
        ActionSetHyperLink albumHyperLink = ActionSetHyperLink.withText(album.getName())
                .andExternalUrl(album.getExternalUrls().get("spotify")).andUri(album.getUri());
        albumHyperLink.setFont(Font.font(28));
        HBox artistHyperLinkBox = createArtistHyperLinkBox(album);
        getChildren().addAll(albumCoverImageView, albumHyperLink, artistHyperLinkBox);
    }

    private HBox createArtistHyperLinkBox(Album album) {
        HBox artistHyperLinkBox = new HBox();
        for (ArtistSimplified artist : album.getArtists()) {
            ActionSetHyperLink artistHyperLink = ActionSetHyperLink.withText(artist.getName())
                    .andExternalUrl(artist.getExternalUrls().get("spotify"))
                    .andUri(artist.getUri());
            artistHyperLink.setFont(Font.font(18));
            artistHyperLinkBox.getChildren().add(artistHyperLink);
        }
        artistHyperLinkBox.setAlignment(Pos.TOP_CENTER);
        return artistHyperLinkBox;
    }

}
