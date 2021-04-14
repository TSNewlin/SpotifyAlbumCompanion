package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.layout.GridPane.getColumnIndex;

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
        recommendedAlbumsGrid.getChildren().removeIf(node -> getColumnIndex(node) == 0);
        AlbumSimplified[] albums = recommendations.getAlbums();
        for (int i = 0; i < albums.length; i++) {
            recommendedAlbumsGrid.add(new Text(albums[i].getName()), 0, i);
        }
    }

    public void addAlbumTitle(Album album) {
        albumTitleLabel.setText(toTitleCase(album.getName()));
    }

    private String toTitleCase(String givenString) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : givenString.split(" ")) {
            stringBuilder.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private void formatGrid() {
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
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
