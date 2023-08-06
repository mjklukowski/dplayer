package com.github.mjklukowski.dplayer.audio;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.services.DiscordPlaybackService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.channel.VoiceChannel;

public class TrackScheduler extends AudioEventAdapter {

    private VoiceChannel channel;
    private final DiscordService discordService;
    private final DiscordPlaybackService playbackService;
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    public TrackScheduler(VoiceChannel channel, DiscordService discordService, DiscordPlaybackService playbackService, AudioPlayerManager playerManager, AudioPlayer player) {
        this.channel = channel;
        this.discordService = discordService;
        this.playbackService = playbackService;
        this.playerManager = playerManager;
        this.player = player;
    }

    public void play(Track track) {
        playerManager.loadItem(track.getUrl().toString(), new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                player.playTrack(audioTrack);
                discordService.sendMessage(channel.getGuild().block(), "Now playing" + track.getUrl());
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    public void setChannel(VoiceChannel channel) {
        this.channel = channel;
    }
}
