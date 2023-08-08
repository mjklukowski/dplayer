package com.github.mjklukowski.dplayer.audio;

import com.github.mjklukowski.dplayer.player.domain.Track;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.function.Consumer;

public class AudioLoader implements AudioLoadResultHandler {

    private final AudioPlayerManager manager;
    private Consumer<AudioTrack> onLoad;
    private Consumer<String> onError;

    private AudioLoader(AudioPlayerManager manager) {
        this.manager = manager;
    }

    public void loadTrack(Track track) {
        manager.loadItem(track.url().toString(), this);
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        onLoad.accept(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        // not used
    }

    @Override
    public void noMatches() {
        onError.accept("Not found");
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        onError.accept(exception.getMessage());
    }

    public static Builder builder(AudioPlayerManager manager) {
        return new Builder(manager);
    }

    public static class Builder {

        private final AudioPlayerManager manager;
        AudioLoader loader;

        private Builder(AudioPlayerManager manager) {
            this.manager = manager;
            reset();
        }

        private void reset() {
            loader = new AudioLoader(manager);
        }

        public Builder onLoad(Consumer<AudioTrack> onLoadCallback) {
            loader.onLoad = onLoadCallback;
            return this;
        }

        public Builder onError(Consumer<String> onErrorCallback) {
            loader.onError = onErrorCallback;
            return this;
        }

        public AudioLoader build() {
            AudioLoader loader = this.loader;
            reset();
            return loader;
        }

    }
}
