package com.github.mjklukowski.dplayer.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchResultSnippet;
import com.github.mjklukowski.dplayer.youtube.dto.YouTubeThumbnail;

import java.util.List;
import java.util.Map;

public class YouTubeVideo {

    private String id;
    private YouTubeSearchResultSnippet data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore()
    public YouTubeSearchResultSnippet getData() {
        return data;
    }

    @JsonSetter("snippet")
    public void setData(YouTubeSearchResultSnippet data) {
        this.data = data;
    }

    public String getChannelTitle() {
        return data.channelTitle();
    }

    public String getDescription() {
        return data.description();
    }

    public String getTitle() {
        return data.title();
    }

    public String getThumbnail() {
        Map<String, YouTubeThumbnail> thumbnails = data.thumbnails();
        for(String res : List.of("high", "medium", "default")) {
            if(!thumbnails.containsKey(res))
                continue;

            return thumbnails.get(res).url();
        }

        return "";
    }
}
