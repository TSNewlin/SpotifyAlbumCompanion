package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Album;
import edu.bsu.cs222.spotifycompanion.model.AlbumRecommendations;
import edu.bsu.cs222.spotifycompanion.model.InformationType;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiApplicant;
import edu.bsu.cs222.spotifycompanion.model.SpotifyApiInitializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private final RecommendationsArea recommendationsArea = new RecommendationsArea();
    private final SpotifyWebApiExceptionAlert spotifyWebApiExceptionAlert = new SpotifyWebApiExceptionAlert();
    private SpotifyApiApplicant apiApplicant;
    private Album foundAlbum;


    @Override
    public void start(Stage primaryStage) {
        this.apiApplicant = initializeApiApplicant();
        Parent ui = setUpUI();
        primaryStage.setScene(new Scene(ui));
        primaryStage.setTitle("Album Companion");
        primaryStage.show();
    }

    private SpotifyApiApplicant initializeApiApplicant() {
        try {
            return new SpotifyApiApplicant(new SpotifyApiInitializer().initializeApi());
        } catch (SpotifyWebApiException exception) {
            Alert failureToInitializeApiAlert = new Alert(Alert.AlertType.WARNING);
            failureToInitializeApiAlert.setContentText("Could not get Api access token from Spotify, please restart " +
                    "application.");
            return new SpotifyApiApplicant(new SpotifyApi.Builder().build());
        }
    }

    private Parent setUpUI() {
        GridPane mainGrid = new GridPane();
        VBox recommendationArea = setUpRecommendedArea();
        VBox informationInputArea = setUpInformationInputArea();
        VBox spotifyLogo = setUpSpotifyLogoImage();
        ScrollPane informationViewArea = setUpInformationViewArea();
        mainGrid.add(recommendationArea, 1, 0, 1, 2);
        mainGrid.add(informationInputArea, 0, 0);
        mainGrid.add(spotifyLogo, 1, 2);
        mainGrid.add(informationViewArea, 0, 1);
        return mainGrid;
    }

    private VBox setUpRecommendedArea() {
        recommendationsArea.addListener(() -> searchForRecommendationsOf(foundAlbum));
        return new VBox(recommendationsArea);
    }

    private VBox setUpInformationInputArea() {
        InformationInputArea informationInputArea = new InformationInputArea();
        informationInputArea.addListener(new InformationInputArea.Listener() {
            @Override
            public void onAlbumTitleSpecified(String albumTitle) {
                querySpotifyForAlbum(albumTitle);
            }

            @Override
            public void onInformationTypeSelected(InformationType informationType) {
                changeSeenOutput(informationType);
            }
        });
        return new VBox(informationInputArea);
    }

    private VBox setUpSpotifyLogoImage() {
        Image image = new Image("Spotify_Logo_CMYK_Green (Resized).png");
        ImageView imageView = new ImageView(image);
        VBox logoBox = new VBox(imageView);
        logoBox.setPadding(new Insets(30));
        logoBox.setAlignment(Pos.CENTER_RIGHT);
        return logoBox;
    }

    private ScrollPane setUpInformationViewArea() {
        VBox innerScrollBox = new VBox();
        innerScrollBox.getChildren().addAll(tracksView, factsView);
        factsView.setVisible(true);
        factsView.setManaged(true);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(innerScrollBox);
        scrollPane.setPrefHeight(300);
        scrollPane.setPrefWidth(300);
        return scrollPane;
    }

    private void querySpotifyForAlbum(String albumTitle) {
        executor.execute(() -> Platform.runLater(() -> {
            try {
                Album album = apiApplicant.searchForAlbum(albumTitle);
                this.foundAlbum = album;
                factsView.show(album);
                tracksView.show(album);
                recommendationsArea.addAlbumTitle(album);
            } catch (SpotifyWebApiException exception) {
                spotifyWebApiExceptionAlert.showAlert(exception);
            }
        }));
    }

    private void searchForRecommendationsOf(Album album) {
        executor.execute(() -> Platform.runLater(() ->{
            try{
                AlbumRecommendations recommendations = apiApplicant.searchForRecommendations(album);
                recommendationsArea.show(recommendations);
            } catch (SpotifyWebApiException exception) {
                spotifyWebApiExceptionAlert.showAlert(exception);
            }
        }));
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

}