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
        setPrefWidth(80);
        getChildren().addAll(switchLabel, switchButton);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
        switchLabel.setAlignment(Pos.CENTER);
    }

    private void configureSwitchLabel() {
        switchLabel = new Label("Facts");
        switchLabel.prefWidthProperty().bind(widthProperty().divide(2));
        switchLabel.prefHeightProperty().bind(heightProperty().divide(2));
        switchLabel.setOnMouseClicked(event -> changeSelectedFilter());
    }

    private void configureSwitchButton() {
        switchButton = new Button();
        switchButton.prefWidthProperty().bind(widthProperty().divide(2));
        switchButton.prefHeightProperty().bind(heightProperty().divide(2));
        switchButton.setOnMouseClicked(event -> changeSelectedFilter());
    }

    public void changeSelectedFilter() {
        Platform.runLater(() -> {
            if (selectedFilterType.equals(InformationType.FACTS)) {
                selectedFilterType = InformationType.TRACKS;
                switchLabel.setText("Tracks");
                switchLabel.toFront();
            }
            else {
                selectedFilterType = InformationType.FACTS;
                switchLabel.setText("Facts");
                switchButton.toFront();
            }
        });
    }

    public InformationType getSelectedFilterType() {
        return selectedFilterType;
    }
}