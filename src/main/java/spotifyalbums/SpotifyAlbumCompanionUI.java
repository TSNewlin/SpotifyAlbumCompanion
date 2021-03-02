package spotifyalbums;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SpotifyAlbumCompanionUI extends Application {


    @Override
    public void start(Stage primaryStage) {
        Parent ui = createUI();
        primaryStage.setScene(new Scene(ui));
        primaryStage.show();
    }

    private Parent createUI() {
        VBox inputArea = createInputArea();
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        VBox ui = new VBox();
        ui.getChildren().addAll(inputArea, outputArea);
        return ui;
    }

    private VBox createInputArea(){
        TextField searchBar = new TextField();
        ComboBox<String> searchOptions = new ComboBox<>();
        Button searchButton = new Button("Find album information");
        VBox inputArea = new VBox();
        inputArea.getChildren().addAll(searchBar, searchOptions, searchButton);
        return inputArea;
    }


}