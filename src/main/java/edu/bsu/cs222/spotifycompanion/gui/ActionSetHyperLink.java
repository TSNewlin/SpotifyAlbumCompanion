package edu.bsu.cs222.spotifycompanion.gui;

import javafx.scene.control.Hyperlink;

import java.net.URI;
import java.util.Objects;

public class ActionSetHyperLink extends Hyperlink {

    public static Builder withText(String text){
        return new Builder(text);
    }

    public static final class Builder {
        private final String text;
        private URI externalWebLinkUri;
        private URI spotifyUri;

        public Builder(String text){
            Objects.requireNonNull(text);
            this.text = text;
        }

        public Builder andExternalUrl(String externalUrl){
            Objects.requireNonNull(externalUrl);
            this.externalWebLinkUri = URI.create(externalUrl);
            return this;
        }

        public ActionSetHyperLink andUri(String uri){
            Objects.requireNonNull(uri);
            this.spotifyUri = URI.create(uri);
            return new ActionSetHyperLink(this);
        }
    }

    private final URI externalWebLinkUri;
    private final URI spotifyUri;

    private ActionSetHyperLink(Builder builder) {
        super(builder.text);
        this.externalWebLinkUri = builder.externalWebLinkUri;
        this.spotifyUri = builder.spotifyUri;
    }


}
