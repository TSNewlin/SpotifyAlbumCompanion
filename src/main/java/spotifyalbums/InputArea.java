package spotifyalbums;

import com.wrapper.spotify.SpotifyApi;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
        getChildren().addAll(queryArea);
        setAlignment(Pos.CENTER);
    }

    private Button createSearchButton() {
        Objects.requireNonNull(queryField, "Query field must be made before the button");
        Button searchButton = new Button("Album Search");
        searchButton.setOnAction(event -> fireOnAlbumTitleSpecified());
        return searchButton;
    }

    public Node createInputArea() {
        HBox innerQueryArea = createInnerInputArea();
        ChoiceBox<String> filterBox = createFilterBox();
        HBox inputBox = new HBox(35);
        inputBox.setPadding(new Insets(5, 0, 5, 0));
        inputBox.getChildren().addAll(innerQueryArea, filterBox);
        return inputBox;
    }

    private HBox createInnerInputArea() {
        queryField = new TextField();
        queryField.setOnAction(event -> fireOnAlbumTitleSpecified());
        queryField.setPromptText("Enter an album name.");
        queryField.setPrefWidth(220);
        Button searchButton = createSearchButton();
        HBox innerQueryAreaBox = new HBox();
        innerQueryAreaBox.getChildren().addAll(queryField, searchButton);
        return innerQueryAreaBox;
    }

    private ChoiceBox<String> createFilterBox() {
        ChoiceBox<String> filterBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "All informtion", "Album Information", "Track Information"));
        filterBox.getSelectionModel().selectFirst();
        return filterBox;
    }
}
