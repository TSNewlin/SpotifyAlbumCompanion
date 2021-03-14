package spotifyalbums.gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import spotifyalbums.model.InformationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputArea extends VBox {

    private final List<Listener> eventListeners = new ArrayList<>();
    private final BooleanProperty searchEnabled = new SimpleBooleanProperty(false);
    private TextField queryField;
    private ComboBox<InformationType> filterBox;

    public InputArea() {
        Node queryArea = createInputArea();
        getChildren().addAll(queryArea);
        configureSearchEnablingBindingProperty();
        setAlignment(Pos.CENTER);
    }

    private Button createSearchButton() {
        Objects.requireNonNull(queryField, "Query field must be made before the button");
        Button searchButton = new Button("Album Search");
        searchButton.disableProperty().bind(searchEnabled.not());
        searchButton.setOnAction(event -> fireOnAlbumTitleSpecified());
        return searchButton;
    }

    private void configureSearchEnablingBindingProperty() {
        BooleanBinding textAvailabilityBinding = new BooleanBinding() {
            {
                bind(queryField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return !queryField.getText().trim().isEmpty();
            }
        };
        searchEnabled.bind(textAvailabilityBinding);
    }

    private Node createInputArea() {
        HBox innerQueryArea = createInnerInputArea();
        setupFilterBox();
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

    private void setupFilterBox() {
        filterBox = new ComboBox<>(FXCollections.observableArrayList(
                InformationType.FACTS, InformationType.TRACKS));
        filterBox.setPromptText("Choose information.");
        filterBox.setOnAction(event -> fireOnInformationTypeSelected());
    }

    public interface Listener {
        void onAlbumTitleSpecified(String albumTitle);
        void onInformationTypeSelected(InformationType informationType);
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

    private void fireOnInformationTypeSelected() {
        for (Listener eventListener : eventListeners) {
            eventListener.onInformationTypeSelected(filterBox.getValue());
        }
    }
}
