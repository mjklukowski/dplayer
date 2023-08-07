package com.github.mjklukowski.dplayer.audio;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.services.PlaybackService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioProvider;

public class AudioManager extends AudioEventAdapter {

    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;
    private final AudioProvider provider;
    private final VoiceChannel channel;
    private final DiscordService discordService;
    private final PlaybackService playbackService;

    public AudioManager(VoiceChannel channel, DiscordService discordService, PlaybackService playbackService) {
        this.channel = channel;
        this.discordService = discordService;
        this.playbackService = playbackService;
        playerManager = new DefaultAudioPlayerManager();
        player = createAudioPlayer(playerManager);
        provider = new LavaPlayerAudioProvider(player);
    }

    private AudioPlayer createAudioPlayer(AudioPlayerManager playerManager) {
        playerManager.getConfiguration()
                .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioPlayer player = playerManager.createPlayer();
        player.addListener(this);
        return player;
    }

    public AudioProvider getProvider() {
        return provider;
    }

    public void playTrack(Track track) {
        AudioLoader loader = AudioLoader.builder(playerManager)
                .onLoad(audioTrack -> {
                    player.stopTrack();
                    player.setPaused(false);
                    player.playTrack(audioTrack);
                    discordService.sendMessage(channel.getGuild().block(), "Now playing" + track.getUrl());
                })
                .onError(message -> playbackService.next(channel))
                .build();

        loader.loadTrack(track);
    }

    public void resumeTrack() {
        player.setPaused(false);
    }

    public void pauseTrack() {
        player.setPaused(true);
    }

    public void stopTrack() {
        player.stopTrack();
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        playbackService.next(channel);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason == AudioTrackEndReason.FINISHED)
            playbackService.next(channel);
    }
}
