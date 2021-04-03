package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.layout.GridPane.getColumnIndex;

public class AlbumRecommendationsUI extends VBox {

    private final VBox header = new VBox();
    private final Label albumTitleLbl = new Label("(Your Album)");
    private final GridPane grid = new GridPane();
    private final List<AlbumRecommendationsUI.Listener> eventListeners = new ArrayList<>();

    public AlbumRecommendationsUI() {
        getChildren().addAll(setUpRecommendationsHeader(), formatGrid());
    }

    public void show(AlbumRecommendations recommendations) {
        grid.getChildren().removeIf(node -> getColumnIndex(node) == 0);
        AlbumSimplified[] albums = recommendations.getAlbums();
        for (int i = 0; i < albums.length; i++) {
            grid.add(new Text(albums[i].getName()), 0, i);
        }
    }

    private VBox setUpRecommendationsHeader() {
        header.setAlignment(Pos.TOP_CENTER);
        header.setMinSize(300, 50);
        header.getChildren().addAll(albumTitleLbl, getRecommendationsButton());
        return header;
    }

    private Button getRecommendationsButton() {
        Button recommendationsButton = new Button("Search For Recommendations");
        recommendationsButton.setOnAction(event -> fireOnRecommendationsButtonPressed());
        return recommendationsButton;
    }

    public void addAlbumTitle(String title) {
        title = toTitleCase(title);
        albumTitleLbl.setText(title);
    }

    private String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : arr) {
            stringBuilder.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private ScrollPane formatGrid() {
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            grid.getColumnConstraints().add(column);
        }
        grid.setHgap(10);
        grid.setVgap(10);
        ScrollPane pane = new ScrollPane();
        pane.setContent(grid);
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
