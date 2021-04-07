package edu.bsu.cs222.spotifycompanion.gui;

import edu.bsu.cs222.spotifycompanion.model.InformationType;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FilterSwitch extends HBox {

    private InformationType selectedFilterType = InformationType.FACTS;
    private Label switchLabel;
    private Button switchButton;

    public FilterSwitch() {
        configureSwitchLabel();
        configureSwitchButton();
        setPrefWidth(100);
        getChildren().addAll(switchButton, switchLabel);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
        switchLabel.setAlignment(Pos.CENTER);
    }

    private void configureSwitchLabel() {
        switchLabel = new Label("Tracks");
        switchLabel.prefWidthProperty().bind(widthProperty().divide(2));
        switchLabel.prefHeightProperty().bind(heightProperty().divide(2));
        switchLabel.setOnMouseClicked(event -> changeSelectedFilter());
    }

    private void configureSwitchButton() {
        switchButton = new Button("Facts");
        switchButton.prefWidthProperty().bind(widthProperty().divide(2));
        switchButton.prefHeightProperty().bind(heightProperty().divide(2));
        switchButton.setOnMouseClicked(event -> changeSelectedFilter());
    }

    public void changeSelectedFilter() {
        Platform.runLater(() -> {
            if (selectedFilterType.equals(InformationType.FACTS)) {
                selectedFilterType = InformationType.TRACKS;
                switchLabel.setText("Facts");
                switchButton.setText("Tracks");
                switchButton.toFront();
            }
            else {
                selectedFilterType = InformationType.FACTS;
                switchLabel.setText("Tracks");
                switchButton.setText("Facts");
                switchLabel.toFront();
            }
        });
    }

    public InformationType getSelectedFilterType() {
        return selectedFilterType;
    }
}