package com.github.mjklukowski.dplayer.youtube.dto.playlist;

import com.fasterxml.jackson.annotation.JsonSetter;

public class YouTubePlaylistItem {
    private YoutubePlaylistSnippet data;

    public YoutubePlaylistSnippet getData() {
        return data;
    }

    @JsonSetter("snippet")
    public void setData(YoutubePlaylistSnippet data) {
        this.data = data;
    }

}
