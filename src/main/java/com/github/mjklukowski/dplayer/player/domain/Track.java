package com.github.mjklukowski.dplayer.player.domain;

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

    public URL url() {
        return url;
    }

    public String title() {
        return title;
    }

    public String thumbnail() {
        return thumbnail;
    }

}
