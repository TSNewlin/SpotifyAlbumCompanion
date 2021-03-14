package spotifyalbums.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import spotifyalbums.model.InformationType;
import spotifyalbums.model.SpotifyAlbumSearcher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final FactsView factsView = new FactsView();
    private final TracksView tracksView = new TracksView();
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
        inputArea.addListener(new InputArea.Listener() {
            @Override
            public void onAlbumTitleSpecified(String albumTitle) {
                querySpotifyForAlbum(albumTitle);
            }

            @Override
            public void onInformationTypeSelected(InformationType informationType) {
                changeSeenOutput(informationType);
            }
        });
        ScrollPane bottomArea = setUpBottomArea();
        return new VBox(inputArea, bottomArea);
    }

    private ScrollPane setUpBottomArea() {
        VBox innerScrollBox = new VBox();
        innerScrollBox.getChildren().addAll(tracksView, factsView);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(innerScrollBox);
        scrollPane.setPrefHeight(300);
        return scrollPane;
    }

    private void changeSeenOutput(InformationType informationType) {
        Platform.runLater(() -> {
            if (informationType == InformationType.FACTS) {
                factsView.setVisible(true);
                tracksView.setVisible(false);
            }
            if (informationType == InformationType.TRACKS) {
                tracksView.setVisible(true);
                factsView.setVisible(false);
            }
        });
    }

    private void querySpotifyForAlbum(String albumTitle) {
        executor.execute(() -> Platform.runLater(() -> {
            try {
                SpotifyAlbumSearcher searcher = new SpotifyAlbumSearcher(albumTitle);
                Album album = searcher.searchForAlbum();
                factsView.show(album);
                tracksView.show(album);
            } catch (SpotifyWebApiException exception) {
                showAlert(exception);
            }
        }));
    }

    private void showAlert(SpotifyWebApiException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("There was an error while making requests to spotify\n" +
                "or the album you are searching for does not exist.");
        alert.setContentText("See stacktrace for more details.");
        alert.getDialogPane().setExpandableContent(setupStackTraceContent(exception));
        alert.show();
    }

    private TextArea setupStackTraceContent(SpotifyWebApiException exception) {
        TextArea messageArea = new TextArea(convertStackTraceToString(exception));
        messageArea.setWrapText(true);
        messageArea.setWrapText(false);
        return messageArea;
    }

    private String convertStackTraceToString(SpotifyWebApiException exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}