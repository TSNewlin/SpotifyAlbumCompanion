package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import edu.bsu.cs222.spotifycompanion.model.InformationType;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiApplicant;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiInitializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpotifyAlbumCompanionUI extends Application {

    private final FactsView factsView = new FactsView();
    private final TracksView tracksView = new TracksView();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final AlbumRecommendationsUI albumRecommendationsUI = new AlbumRecommendationsUI();
    private final GridPane gridPane = new GridPane();
    private final SpotifyWebApiExceptionAlert spotifyWebApiExceptionAlert = new SpotifyWebApiExceptionAlert();
    private SpotifyApiApplicant apiApplicant;
    private Album foundAlbum;


    @Override
    public void start(Stage primaryStage) throws SpotifyWebApiException {
        this.apiApplicant = new SpotifyApiApplicant(new SpotifyApiInitializer().initializeApi());
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
        albumRecommendationsUI.addListener(() -> searchForRecommendationsOf(foundAlbum));
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
                Album album = apiApplicant.searchForAlbum(albumTitle);
                factsView.show(album);
                tracksView.show(album);
                this.foundAlbum = album;
            } catch (SpotifyWebApiException exception) {
                spotifyWebApiExceptionAlert.showAlert(exception);
            }
        }));
    }

    private void searchForRecommendationsOf(Album album) {
        executor.execute(() -> {
            try{
                AlbumRecommendations recommendations = apiApplicant.searchForRecommendations(album);
            } catch (SpotifyWebApiException exception) {
                spotifyWebApiExceptionAlert.showAlert(exception);
            }
        });
    }

    private void addRecommendedAlbumTitle(String albumTitle) {
        albumRecommendationsUI.addAlbumTitle(albumTitle);
    }

}