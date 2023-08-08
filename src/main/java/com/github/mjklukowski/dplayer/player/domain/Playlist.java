package com.github.mjklukowski.dplayer.player.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Playlist extends TrackList {
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Playlist(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
