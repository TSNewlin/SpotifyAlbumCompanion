package spotifyalbums;

import com.wrapper.spotify.SpotifyApi;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputArea extends VBox {

    SpotifyApiInitializer spotifyApiInitializer = new SpotifyApiInitializer();
    private final List<Listener> eventListeners = new ArrayList<>();
    private TextField queryField;

    public interface Listener {
        void onAlbumTitleSpecified(String albumTitle);
    }

    public void addListener(Listener listener) {
        Objects.requireNonNull(listener);
        eventListeners.add(listener);
    }

    private void fireOnAlbumTitleSpecified() {
        String albumTitle = queryField.getText();
        for (Listener eventListener : eventListeners) {
            eventListener.onAlbumTitleSpecified(albumTitle);
        }
    }

    public InputArea() {
        Node queryArea = createInputArea();
        Button searchButton = createSearchButton();
        getChildren().addAll(queryArea, searchButton);
        setAlignment(Pos.CENTER);
    }

    private Button createSearchButton() {
        Objects.requireNonNull(queryField, "Query field must be made before the button");
        Button searchButton = new Button("Album Search");
        searchButton.setOnAction(event -> fireOnAlbumTitleSpecified());
        return searchButton;
    }

    public Node createInputArea() {
        VBox innerQueryArea = createInnerInputArea();
        HBox inputBox = new HBox();
        inputBox.getChildren().add(innerQueryArea);
        inputBox.setAlignment(Pos.CENTER);
        return inputBox;
    }

    private VBox createInnerInputArea() {
        queryField = new TextField();
        queryField.setOnAction(event -> fireOnAlbumTitleSpecified());
        VBox innerQueryAreaBox = new VBox();
        innerQueryAreaBox.setAlignment(Pos.CENTER);
        innerQueryAreaBox.getChildren().addAll(new Label("Album Title: "), queryField);
        return innerQueryAreaBox;
    }
}
