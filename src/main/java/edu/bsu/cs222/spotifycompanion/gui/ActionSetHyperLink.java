package edu.bsu.cs222.spotifycompanion.gui;

import javafx.scene.control.Hyperlink;

import java.net.URI;

public class ActionSetHyperLink extends Hyperlink {

    private final String externalUrl;
    private final URI uri;

    public ActionSetHyperLink(String text, String externalUrl, String uri) {
        super(text);
        this.externalUrl = externalUrl;
        this.uri = URI.create(uri);
    }


}
