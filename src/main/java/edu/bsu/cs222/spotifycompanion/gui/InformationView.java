package edu.bsu.cs222.spotifycompanion.gui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public abstract class InformationView extends GridPane {

    public InformationView() {
        initializeGrid();
    }

    private void initializeGrid() {
        setManaged(false);
        setVisible(false);
        setVgap(10);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(125);
            getColumnConstraints().add(column);
        }

    }

}
