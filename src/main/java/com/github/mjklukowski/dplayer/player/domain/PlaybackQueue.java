package com.github.mjklukowski.dplayer.player.domain;

import java.util.Optional;

public class PlaybackQueue extends TrackList {

    int currentTrack = 0;

    public Optional<Track> next() {
        currentTrack++;

        if(currentTrack >= trackList.size()) {
            currentTrack = 0;
            return Optional.empty();
        }

        return Optional.of(trackList.get(currentTrack));
    }

    public Optional<Track> prev() {
        currentTrack--;

        if(trackList.isEmpty())
            return Optional.empty();

        if(currentTrack < 0)
            currentTrack = 0;

        return Optional.of(trackList.get(currentTrack));
    }

    public Optional<Track> get(int trackIndex) {
        if(trackIndex >= 0 && trackIndex < trackList.size()) {
            currentTrack = trackIndex;
            return Optional.of(trackList.get(trackIndex));
        }
        return Optional.empty();
    }

    public Optional<Track> current() {
        return get(currentTrack);
    }

    @Override
    public void remove(int trackIndex) {
        super.remove(trackIndex);
        if(trackIndex <= currentTrack)
            currentTrack--;
    }
}
