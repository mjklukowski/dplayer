package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.random.RandomGenerator;

public class QueueShuffle implements QueueStrategy {

    private final Stack<Track> history = new Stack<>();
    private final RandomGenerator random;


    public QueueShuffle(RandomGenerator random) {
        this.random = random;
    }

    public QueueShuffle() {
       this(new Random());
    }


    @Override
    public Track next(List<Track> trackList) {
        List<Track> tracksNotPlayed = getTracksNotPlayed(trackList);

        if(tracksNotPlayed.isEmpty())
            return null;

        int nextTrackIndex = random.nextInt(0, tracksNotPlayed.size());
        Track nextTrack = tracksNotPlayed.get(nextTrackIndex);
        history.push(nextTrack);
        return nextTrack;
    }

    @Override
    public Track prev(List<Track> trackList) {
        if(trackList.isEmpty())
            return null;

        history.pop();

        if(history.isEmpty())
            history.push(trackList.get(0));

        return history.peek();
    }

    @Override
    public Track get(List<Track> trackList, int trackIndex) {
        if(trackIndex >= 0 && trackIndex < trackList.size()) {
            Track track = trackList.get(trackIndex);
            history.push(track);
            return track;
        }
        return null;
    }

    @Override
    public Track getCurrent(List<Track> trackList) {
        if(history.isEmpty()) {
            if(trackList.isEmpty())
                return null;

            history.push(trackList.get(0));
        }
        return history.peek();
    }

    @Override
    public int getCurrentTrackIndex(List<Track> trackList) {
        Track current = getCurrent(trackList);
        int index = trackList.indexOf(current);

        if(index < 0)
            index = 0;

        return index;
    }

    @Override
    public void onRemove(List<Track> trackList, int removedTrackIndex) {
        Track track = trackList.get(removedTrackIndex);
        history.remove(track);
    }

    private List<Track> getTracksNotPlayed(List<Track> trackList) {
        ArrayList<Track> tracksNotPlayed = new ArrayList<>(trackList);
        tracksNotPlayed.removeAll(history);
        return tracksNotPlayed;
    }
}
