package com.github.mjklukowski.dplayer.youtube.dto;

import java.util.Map;

public record YouTubeSearchResultSnippet(
        String title,
        String description,
        Map<String, YouTubeThumbnail> thumbnails,
        String channelTitle
) {}
