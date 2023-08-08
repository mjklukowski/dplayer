package com.github.mjklukowski.dplayer.player.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public final class Track {
    private final URL url;
    private final String title;
    private final String thumbnail;

    public Track(URL url, String title, String thumbnail) {
        this.url = url;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Track{" +
                "url=" + url +
                ", title='" + title + '\'' +
                '}';
    }

    @JsonProperty
    public URL url() {
        return url;
    }

    @JsonProperty
    public String title() {
        return title;
    }

    @JsonProperty
    public String thumbnail() {
        return thumbnail;
    }

}
