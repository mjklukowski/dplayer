package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscordPlaybackService implements PlaybackService {

    private final QueueService queueService;
    private final DiscordService discordService;

    public DiscordPlaybackService(QueueService queueService, DiscordService discordService) {
        this.queueService = queueService;
        this.discordService = discordService;
    }

    private void join(VoiceChannel channel) {
        channel.join(VoiceChannelJoinSpec.builder().provider(AudioProvider.NO_OP).build()).block();
    }

    @Override
    public void play(VoiceChannel channel, int trackIndex) {
        Guild guild = channel.getGuild().block();
        Optional<Track> track = queueService.getQueue(guild).get(trackIndex);
        track.ifPresent(t -> play(channel, t));
    }

    @Override
    public void play(VoiceChannel channel) {
        Guild guild = channel.getGuild().block();
        Optional<Track> track = queueService.getQueue(guild).current();
        track.ifPresent(t -> play(channel, t));
    }

    private void play(VoiceChannel channel, Track track) {
        join(channel);
        discordService.sendMessage(channel.getGuild().block(), "Now playing" + track.getUrl());
    }

    @Override
    public void pause(VoiceChannel channel) {

    }

    @Override
    public void next(VoiceChannel channel) {

    }

    @Override
    public void prev(VoiceChannel channel) {

    }

    @Override
    public void stop(VoiceChannel channel) {
        channel.getVoiceConnection()
                .flatMap(VoiceConnection::disconnect)
                .block();
    }
}
