package com.github.mjklukowski.dplayer.youtube.dto.playlist;

import java.util.List;

public record YouTubePlaylistResponse(String nextPageToken, List<YouTubePlaylistItem> items) {
}
