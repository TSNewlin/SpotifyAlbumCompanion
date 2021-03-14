package spotifyalbums.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public abstract class InformationView extends GridPane {

    public InformationView() {
        initializeGrid();
    }

    private void initializeGrid() {
        this.setPrefHeight(300);
        this.setVgap(10);
        this.setHgap(20);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(150);
            this.getColumnConstraints().add(column);
        }
    }

}
