package edu.bsu.cs222.spotifycompanion.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class SpotifyLogo extends VBox {

    private final Hyperlink spotifyLink = new Hyperlink();

    public SpotifyLogo() {
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER_RIGHT);
        getChildren().add(setUpSpotifyLogoImage());
    }

    private Hyperlink setUpSpotifyLogoImage() {
        ImageView view = new ImageView(new Image("Spotify_Logo_CMYK_Green.png"));
        spotifyLink.setGraphic(view);
        spotifyLink.setOnAction(event -> openGetSpotifyPage());
        return spotifyLink;
    }

    private void openGetSpotifyPage() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            openSpotifyHomePageInDefaultBrowser();
        }
        else {
            spotifyLink.setDisable(true);
        }
    }

    private void openSpotifyHomePageInDefaultBrowser() {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.spotify.com/us/home/"));
        } catch (IOException e) {
            showCantOpenSpotifyAlert();
        }
    }

    private void showCantOpenSpotifyAlert() {
        Alert cantOpenSpotifyPageAlert = new Alert(Alert.AlertType.WARNING);
        cantOpenSpotifyPageAlert.setContentText("Cannot open Spotify home page in your default web browser.");
        cantOpenSpotifyPageAlert.show();
        spotifyLink.setDisable(true);
    }

}


