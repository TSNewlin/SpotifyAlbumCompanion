package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.InformationType;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiApplicant;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        VBox spotifyLogo = createSpotifyLogoImage();
        spotifyLogo.setPadding(new Insets(30));
        spotifyLogo.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(informationUI, 0, 0);
        gridPane.add(bottomArea, 0, 1);
        gridPane.add(recommendedUI, 1, 0, 1, 2);
        gridPane.add(spotifyLogo, 1, 2);
        return gridPane;
    }

    private VBox createSpotifyLogoImage() {
        Image image = new Image("Spotify_Logo_CMYK_Green (Resized).png");
        ImageView imageView = new ImageView(image);
        return new VBox(imageView);
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
                spotifyWebApiExceptionAlert.showAlert(exception);
            }
        }));
    }

    private void addRecommendedAlbumTitle(String albumTitle) {
        albumRecommendationsUI.addAlbumTitle(albumTitle);
    }

}