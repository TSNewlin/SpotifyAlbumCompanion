package spotifyalbums.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final InformationView outputArea = new InformationView();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    @Override
    public void start(Stage primaryStage) {
        Parent ui = createUI();
        primaryStage.setScene(new Scene(ui));
        primaryStage.setTitle("Album Companion");
        primaryStage.show();
    }

    private Parent createUI() {
        InputArea inputArea = new InputArea();
        inputArea.addListener(this::findAlbumInformationOf);
        return new VBox(inputArea, outputArea);
    }

    private void findAlbumInformationOf(String albumTitle) {
        executor.execute(() -> Platform.runLater(() -> {
            try {
                outputArea.showAlbumInformationOf(albumTitle);
            } catch (SpotifyWebApiException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }));
    }

}