package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;

import java.util.List;

public interface QueueStrategy {

    Track next(List<Track> trackList);
    Track prev(List<Track> trackList);
    Track get(List<Track> trackList, int trackIndex);
    Track getCurrent(List<Track> trackList);
    int getCurrentTrackIndex(List<Track> trackList);
    void onRemove(List<Track> trackList, int removedTrackIndex);
}
