package edu.bsu.cs222.spotifycompanion.gui;

import edu.bsu.cs222.spotifycompanion.model.InformationType;
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
    private final FilterSwitch filterSwitch = new FilterSwitch();
    private final TextField searchBar = new TextField();

    public InputArea() {
        Node queryArea = setUpInputArea();
        getChildren().add(queryArea);
        setUpFilterSwitchEvents();
        configureSearchEnablingBindingProperty();
        setAlignment(Pos.CENTER);
    }

    private Node setUpInputArea() {
        HBox innerQueryArea = setUpSearchArea();
        HBox inputBox = new HBox(35);
        inputBox.setPadding(new Insets(5, 0, 5, 0));
        inputBox.getChildren().addAll(innerQueryArea, filterSwitch);
        return inputBox;
    }

    private void setUpFilterSwitchEvents() {
        Node LabelNode, ButtonNode;
        LabelNode = filterSwitch.getChildren().get(0);
        ButtonNode = filterSwitch.getChildren().get(1);
        LabelNode.setOnMouseClicked(event -> fireOnInformationTypeSelected());
        ButtonNode.setOnMouseClicked(event -> fireOnInformationTypeSelected());
    }

    private void configureSearchEnablingBindingProperty() {
        BooleanBinding textAvailabilityBinding = new BooleanBinding() {
            {
                bind(searchBar.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return !searchBar.getText().trim().isEmpty();
            }
        };
        searchEnabled.bind(textAvailabilityBinding);
    }

    private HBox setUpSearchArea() {
        setUpSearchBar();
        Button searchButton = createSearchButton();
        HBox searchAreaBox = new HBox();
        searchAreaBox.getChildren().addAll(searchBar, searchButton);
        return searchAreaBox;
    }

    private void setUpSearchBar() {
        searchBar.setOnAction(event -> fireOnAlbumTitleSpecified());
        searchBar.setPromptText("Enter an album name.");
        searchBar.setPrefWidth(220);
    }

    private Button createSearchButton() {
        Objects.requireNonNull(searchBar, "Query field must be made before the button");
        Button searchButton = new Button();
        Label searchButtonLabel = new Label("\u2315");
        searchButton.setGraphic(searchButtonLabel);
        searchButtonLabel.setRotate(-90);
        searchButton.disableProperty().bind(searchEnabled.not());
        searchButton.setOnAction(event -> fireOnAlbumTitleSpecified());
        return searchButton;
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
        String albumTitle = searchBar.getText();
        for (Listener eventListener : eventListeners) {
            eventListener.onAlbumTitleSpecified(albumTitle);
        }
    }

    private void fireOnInformationTypeSelected() {
        for (Listener eventListener : eventListeners) {
            eventListener.onInformationTypeSelected(filterSwitch.getSelectedFilterType());
        }
        filterSwitch.changeSelectedFilter();
    }
}
