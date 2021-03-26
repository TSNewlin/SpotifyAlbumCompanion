package edu.bsu.cs222.spotifycompanion.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AlbumRecommendationsUI extends InformationView {

    private final VBox header = new VBox();
    private final Label albumTitleLbl = new Label("(Your Album)");

    public AlbumRecommendationsUI() {
        super();
        setVisible(true);
        setManaged(true);
        getChildren().add(setUpRecommendationsHeader());
    }

    private VBox setUpRecommendationsHeader() {
        header.setAlignment(Pos.TOP_CENTER);
        header.setMinSize(300, 100);
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

}
