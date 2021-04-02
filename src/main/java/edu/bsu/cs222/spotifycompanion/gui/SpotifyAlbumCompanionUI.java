package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.InformationType;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiApplicant;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final FactsView factsView = new FactsView();
    private final TracksView tracksView = new TracksView();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final AlbumRecommendationsUI albumRecommendationsUI = new AlbumRecommendationsUI();
    private final GridPane gridPane = new GridPane();


    @Override
    public void start(Stage primaryStage) {
        Parent ui = createUI();
        primaryStage.setScene(new Scene(ui));
        primaryStage.setTitle("Album Companion");
        primaryStage.show();
    }

    private Parent createUI() {
        ScrollPane bottomArea = setUpBottomArea();
        VBox recommendedUI = createRecommendedVBox();
        VBox informationUI = createInformationVBox();
        gridPane.add(informationUI, 0, 0);
        gridPane.add(bottomArea, 0, 1);
        gridPane.add(recommendedUI, 1, 0, 1, 2);
        return gridPane;
    }

    private VBox createRecommendedVBox() {
        return new VBox(albumRecommendationsUI);
    }

    private VBox createInformationVBox() {
        InputArea inputArea = new InputArea();
        inputArea.addListener(new InputArea.Listener() {
            @Override
            public void onAlbumTitleSpecified(String albumTitle) {
                querySpotifyForAlbum(albumTitle);
                addRecommendedAlbumTitle(albumTitle);
            }

            @Override
            public void onInformationTypeSelected(InformationType informationType) {
                changeSeenOutput(informationType);
            }
        });
        return new VBox(inputArea);
    }

    private ScrollPane setUpBottomArea() {
        VBox innerScrollBox = new VBox();
        innerScrollBox.getChildren().addAll(tracksView, factsView);
        factsView.setVisible(true);
        factsView.setManaged(true);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(innerScrollBox);
        scrollPane.setPrefHeight(300);
        return scrollPane;
    }

    private void changeSeenOutput(InformationType informationType) {
        Platform.runLater(() -> {
            if (informationType.equals(InformationType.TRACKS)) {
                makeFactsViewVisible();
            }
            if (informationType.equals(InformationType.FACTS)) {
                makeTracksViewVisible();
            }
        });
    }

    private void makeFactsViewVisible() {
        factsView.setVisible(true);
        factsView.setManaged(true);
        tracksView.setVisible(false);
        tracksView.setManaged(false);
    }

    private void makeTracksViewVisible() {
        tracksView.setVisible(true);
        tracksView.setManaged(true);
        factsView.setVisible(false);
        factsView.setManaged(false);
    }

    private void querySpotifyForAlbum(String albumTitle) {
        executor.execute(() -> Platform.runLater(() -> {
            try {
                SpotifyApiApplicant searcher = new SpotifyApiApplicant();
                Album album = searcher.searchForAlbum(albumTitle);
                factsView.show(album);
                tracksView.show(album);
            } catch (SpotifyWebApiException exception) {
                showAlert(exception);
            }
        }));
    }

    private void addRecommendedAlbumTitle(String albumTitle) {
        albumRecommendationsUI.addAlbumTitle(albumTitle);
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