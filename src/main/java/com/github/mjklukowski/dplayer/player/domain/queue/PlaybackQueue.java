package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.domain.TrackList;

import java.util.Optional;

public class PlaybackQueue extends TrackList {

    private QueueStrategy queueStrategy;

    public PlaybackQueue(QueueStrategy queueStrategy) {
        this.queueStrategy = queueStrategy;
    }

    public Optional<Track> next() {
        return Optional.ofNullable(queueStrategy.next(trackList));
    }

    public Optional<Track> prev() {
        return Optional.ofNullable(queueStrategy.prev(trackList));
    }

    public Optional<Track> get(int trackIndex) {
        return Optional.ofNullable(queueStrategy.get(trackList, trackIndex));
    }

    public Optional<Track> current() {
        return Optional.ofNullable(queueStrategy.getCurrent(trackList));
    }

    public int getCurrentTrackIndex() {
        return queueStrategy.getCurrentTrackIndex(trackList);
    }

    @Override
    public void remove(int trackIndex) {
        queueStrategy.onRemove(trackList, trackIndex);
        super.remove(trackIndex);
    }

    public QueueStrategy getQueueStrategy() {
        return queueStrategy;
    }

    public void setQueueStrategy(QueueStrategy queueStrategy) {
        this.queueStrategy = queueStrategy;
    }
}
