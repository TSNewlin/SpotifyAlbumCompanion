package edu.bsu.cs222.spotifycompanion.gui;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SpotifyWebApiExceptionAlert {

    public void showAlert(SpotifyWebApiException exception) {
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
