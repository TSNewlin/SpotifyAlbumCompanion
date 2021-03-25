package edu.bsu.cs222.spotifycompanion.gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
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

    private final List<Listener> eventListeners = new ArrayList<>();
    private final BooleanProperty searchEnabled = new SimpleBooleanProperty(false);
    private final BooleanProperty switchedOn = new SimpleBooleanProperty(false);
    private TextField queryField;
    private Label switchLabel;
    private Button switchButton;

    public InputArea() {
        Node queryArea = createInputArea();
        getChildren().addAll(queryArea);
        configureSearchEnablingBindingProperty();
        setAlignment(Pos.CENTER);
    }

    private Button createSearchButton() {
        Objects.requireNonNull(queryField, "Query field must be made before the button");
        Button searchButton = new Button("\u2315");
        searchButton.setPrefWidth(80);
        searchButton.setRotate(-90);
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
        HBox filterSwitch = createFilterSwitch();
        HBox inputBox = new HBox(35);
        inputBox.setPadding(new Insets(5, 0, 5, 0));
        inputBox.getChildren().addAll(innerQueryArea, filterSwitch);
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

    private HBox createFilterSwitch() {
        Button switchButton = createSwitchButton();
        Label switchLabel = createSwitchLabel();
        HBox filterSwitch = new HBox();
        filterSwitch.getChildren().addAll(switchLabel, switchButton);
        filterSwitch.setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        filterSwitch.setAlignment(Pos.CENTER_LEFT);
        switchLabel.setAlignment(Pos.CENTER);
        return filterSwitch;
    }

    private Button createSwitchButton() {
        switchButton = new Button();
        switchButton.prefWidthProperty().bind(widthProperty().divide(3));
        switchButton.prefHeightProperty().bind(heightProperty().divide(2));
        switchButton.setOnAction(event ->
                fireOnInformationTypeSelected());
        return switchButton;
    }

    private Label createSwitchLabel() {
        switchLabel = new Label("Facts");
        switchLabel.prefWidthProperty().bind(widthProperty().divide(3));
        switchLabel.prefHeightProperty().bind(heightProperty().divide(2));
        switchLabel.setOnMouseClicked(event ->
                fireOnInformationTypeSelected());
        return switchLabel;
    }

    private void changeSeenSwitchPositions() {
        switchedOn.set(!switchedOn.getValue());
        if (switchedOn.getValue()) {
            switchLabel.setText("Tracks");
            switchLabel.toFront();
        } else {
            switchLabel.setText("Facts");
            switchButton.toFront();
        }
    }

    public interface Listener {
        void onAlbumTitleSpecified(String albumTitle);
        void onInformationTypeSelected(Boolean switchState);
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
            eventListener.onInformationTypeSelected(switchedOn.getValue());
        }
        changeSeenSwitchPositions();
    }
}
