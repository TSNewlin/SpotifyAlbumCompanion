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
        setPrefHeight(300);
        setVgap(10);
        setHgap(20);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(150);
            getColumnConstraints().add(column);
        }
    }

}
