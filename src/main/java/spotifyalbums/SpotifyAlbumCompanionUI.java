package spotifyalbums;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
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
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
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
            } catch (IOException | ParseException | SpotifyWebApiException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }));
    }

}