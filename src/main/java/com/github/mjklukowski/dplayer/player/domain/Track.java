package com.github.mjklukowski.dplayer.player.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public class Track {
    private final URL url;
    private String artist;
    private String title;
    private long duration;

    public Track(@JsonProperty("url") URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Track{" +
                "url=" + url +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
