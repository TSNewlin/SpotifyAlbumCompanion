package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecommendationsArea extends VBox {

    private final Label albumTitleLabel = new Label();
    private final GridPane recommendedAlbumsGrid = new GridPane();
    private final BooleanProperty searchRecommendationsEnabled = new SimpleBooleanProperty(false);
    private final List<RecommendationsArea.Listener> eventListeners = new ArrayList<>();

    public RecommendationsArea() {
        formatGrid();
        configureSearchRecommendationsBinding();
        getChildren().addAll(setUpTopBar(), createUpScrollPane());
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

    public void addAlbumTitle(Album album) {
        albumTitleLabel.setText(album.getName());
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

    private void configureSearchRecommendationsBinding() {
        BooleanBinding albumTitleAvailableBinding = new BooleanBinding() {
            {
                bind(albumTitleLabel.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return !albumTitleLabel.getText().isEmpty();
            }
        };
        searchRecommendationsEnabled.bind(albumTitleAvailableBinding);
    }

    private VBox setUpTopBar() {
        VBox topBar = new VBox();
        topBar.setAlignment(Pos.TOP_CENTER);
        topBar.setMinSize(300, 50);
        topBar.getChildren().addAll(albumTitleLabel, setUpRecommendationsButton());
        return topBar;
    }

    private Button setUpRecommendationsButton() {
        Button recommendationsButton = new Button("Search For Recommendations");
        recommendationsButton.disableProperty().bind(searchRecommendationsEnabled.not());
        recommendationsButton.setOnAction(event -> fireOnRecommendationsButtonPressed());
        return recommendationsButton;
    }

    private ScrollPane createUpScrollPane() {
        ScrollPane pane = new ScrollPane();
        pane.setContent(recommendedAlbumsGrid);
        pane.setPrefHeight(285);
        pane.setPrefWidth(300);
        return pane;
    }

    public interface Listener {
        void onRecommendationsButtonPressed();
    }

    public void addListener(Listener listener) {
        Objects.requireNonNull(listener);
        eventListeners.add(listener);
    }

    private void fireOnRecommendationsButtonPressed() {
        for (Listener eventListener : eventListeners) {
            eventListener.onRecommendationsButtonPressed();
        }
    }

}
