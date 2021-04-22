package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

public class RecommendationsArea extends ScrollPane {

    private final GridPane recommendedAlbumsGrid = new GridPane();

    public RecommendationsArea() {
        formatGrid();
        setContent(recommendedAlbumsGrid);
        setPrefHeight(285);
        setPrefWidth(300);
        setStyle("-fx-border-color: transparent;" + "-fx-focus-color: transparent;");
    }

    public void show(AlbumRecommendations recommendations) {
        recommendedAlbumsGrid.getChildren().clear();
        List<AlbumSimplified> recommendedAlbums = recommendations.getRecommendedAlbums();
        addAlbumImages(recommendedAlbums);
        for (int i = 0; i < recommendedAlbums.size(); i++) {
            ActionSetHyperLink hyperLink = ActionSetHyperLink.withText(recommendedAlbums.get(i).getName())
                    .andExternalUrl(recommendedAlbums.get(i).getExternalUrls().get("spotify"))
                    .andUri(recommendedAlbums.get(i).getUri());
            recommendedAlbumsGrid.add(hyperLink, 1, i);
        }
    }

    private void addAlbumImages(List<AlbumSimplified> recommendedAlbums) {
        for (int i = 0; i < recommendedAlbums.size(); i++) {
            Image albumImage = recommendedAlbums.get(i).getImages()[2];
            ImageView albumImageView = new ImageView(albumImage.getUrl());
            recommendedAlbumsGrid.add(albumImageView, 0, i);
        }
    }

    private void formatGrid() {
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(70);
            recommendedAlbumsGrid.getColumnConstraints().add(column);
        }
        recommendedAlbumsGrid.setHgap(10);
        recommendedAlbumsGrid.setVgap(10);
    }

}
