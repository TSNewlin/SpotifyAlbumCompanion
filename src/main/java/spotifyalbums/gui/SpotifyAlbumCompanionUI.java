package spotifyalbums.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import spotifyalbums.model.SpotifyAlbumSearcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final InformationView informationView = new InformationView();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Album album;


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
        return new VBox(inputArea, informationView);
    }

    private void findAlbumInformationOf(String albumTitle) {
        executor.execute(() -> Platform.runLater(() -> {
            try {
                SpotifyAlbumSearcher searcher = new SpotifyAlbumSearcher(albumTitle);
                this.album = searcher.searchForAlbum();
                informationView.show(album);
            } catch (SpotifyWebApiException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }));
    }

}