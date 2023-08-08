package com.github.mjklukowski.dplayer.youtube.dto.playlist;

import com.github.mjklukowski.dplayer.youtube.dto.YouTubeThumbnail;

import java.util.Map;

public record YoutubePlaylistSnippet(
        String title,
        String description,
        Map<String, YouTubeThumbnail> thumbnails,
        String channelTitle,
        Map<String, String> resourceId
) {
}
