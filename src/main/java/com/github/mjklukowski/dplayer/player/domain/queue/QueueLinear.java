package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;

import java.util.List;

public class QueueLinear implements QueueStrategy {

    private int currentTrack;

    public QueueLinear() {
        this(0);
    }

    public QueueLinear(int currentTrack) {
        this.currentTrack = currentTrack;
    }

    @Override
    public Track next(List<Track> trackList) {
        currentTrack++;

        if(currentTrack >= trackList.size()) {
            currentTrack = 0;
            return null;
        }

        return get(trackList, currentTrack);
    }

    @Override
    public Track prev(List<Track> trackList) {
        currentTrack--;

        if(trackList.isEmpty())
            return null;

        if(currentTrack < 0)
            currentTrack = 0;

        return get(trackList, currentTrack);
    }

    @Override
    public Track get(List<Track> trackList, int trackIndex) {
        if(trackIndex >= 0 && trackIndex < trackList.size()) {
            currentTrack = trackIndex;
            return trackList.get(trackIndex);
        }
        return null;
    }

    @Override
    public Track getCurrent(List<Track> trackList) {
        return get(trackList, currentTrack);
    }
    @Override
    public int getCurrentTrackIndex(List<Track> trackList) {
        return currentTrack;
    }

    @Override
    public void onRemove(List<Track> trackList, int removedTrackIndex) {
        if(removedTrackIndex <= currentTrack)
            currentTrack--;
        if(currentTrack < 0)
            currentTrack = 0;
    }

}
