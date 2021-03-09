package spotifyalbums;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final OutputArea outputArea = new OutputArea();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    @Override
    public void start(Stage primaryStage) {
        Parent ui = createUI();
        primaryStage.setScene(new Scene(ui));
        primaryStage.show();
    }

    private Parent createUI() {
        InputArea inputArea = new InputArea();
        inputArea.addListener(this::findAlbumInformationOf);
        return new VBox(inputArea, outputArea);
    }

    private void findAlbumInformationOf(String albumTitle) {

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