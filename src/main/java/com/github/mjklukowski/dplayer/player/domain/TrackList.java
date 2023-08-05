package com.github.mjklukowski.dplayer.player.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackList {

    protected final List<Track> trackList = new ArrayList<>();

    public void add(Track track) {
        trackList.add(track);
    }

    public void add(TrackList trackList) {
        this.trackList.addAll(trackList.getTrackList());
    }

    public void remove(int trackIndex) {
        trackList.remove(trackIndex);
    }

    public void clear() {
        trackList.clear();
    }

    public List<Track> getTrackList() {
        return trackList.stream().toList();
    }

}
