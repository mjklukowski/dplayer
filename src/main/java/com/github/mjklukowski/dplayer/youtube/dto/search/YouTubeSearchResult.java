package com.github.mjklukowski.dplayer.youtube.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchResultSnippet;

import java.util.Map;


public class YouTubeSearchResult {
    private String kind;
    private String id;
    YouTubeSearchResultSnippet data;

    public String getKind() {
        return kind;
    }

    public String getId() {
        return id;
    }

    @JsonProperty("data")
    public YouTubeSearchResultSnippet getData() {
        return data;
    }

    @JsonSetter("snippet")
    public void setData(YouTubeSearchResultSnippet data) {
        this.data = data;
    }

    @JsonSetter("id")
    public void setKindAndId(Map<String, String> properties) {
        kind = properties.get("kind");
        id = switch(kind) {
            case "youtube#video" -> properties.get("videoId");
            case "youtube#playlist" -> properties.get("playlistId");
            default -> throw new IllegalStateException("Unexpected value: " + kind);
        };
    }

    public boolean isVideo() {
        return kind.equals("youtube#video");
    }

    public boolean isPlaylist() {
        return kind.equals("youtube#playlist");
    }
}
