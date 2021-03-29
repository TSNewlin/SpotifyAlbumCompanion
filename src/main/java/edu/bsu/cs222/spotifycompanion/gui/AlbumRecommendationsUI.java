package edu.bsu.cs222.spotifycompanion.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AlbumRecommendationsUI extends VBox {

    private final VBox header = new VBox();
    private final Label albumTitleLbl = new Label("(Your Album)");
    private final GridPane grid = new GridPane();

    public AlbumRecommendationsUI() {
        setVisible(true);
        setManaged(true);
        getChildren().addAll(setUpRecommendationsHeader(), formatGrid());
    }

    private VBox setUpRecommendationsHeader() {
        header.setAlignment(Pos.TOP_CENTER);
        header.setMinSize(300, 50);
        header.getChildren().addAll(albumTitleLbl, getRecommendationsButton());
        return header;
    }

    private Button getRecommendationsButton() {
        return new Button("Find Album Recommendations");
    }

    public void addAlbumTitle(String title) {
        title = toTitleCase(title);
        albumTitleLbl.setText(title);
    }

    private String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : arr) {
            stringBuilder.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private GridPane formatGrid() {
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            grid.getColumnConstraints().add(column);
        }
        grid.setHgap(10);
        grid.setVgap(10);
        for (int i = 0; i <= 5; i++) {
            grid.add(new Text("#" + i), 0, i);
            grid.add(new Text(("album")), 1, i);
        }
        return grid;
    }

}
