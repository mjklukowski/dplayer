package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.queue.PlaybackQueue;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueLinear;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueShuffle;
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
        if(channel.getVoiceConnection().block() != null)
            return;
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
        Optional<Track> next = queueService.getQueue(channel.getGuild().block()).next();
        next.ifPresent(track -> play(channel, track));
    }

    @Override
    public void prev(VoiceChannel channel) {
        Optional<Track> prev = queueService.getQueue(channel.getGuild().block()).prev();
        prev.ifPresent(track -> play(channel, track));
    }

    @Override
    public void stop(VoiceChannel channel) {
        channel.getVoiceConnection()
                .flatMap(VoiceConnection::disconnect)
                .block();
    }

    @Override
    public void shuffle(VoiceChannel channel, boolean enabled) {
        PlaybackQueue queue = queueService.getQueue(channel.getGuild().block());
        if(enabled)
            queue.setQueueStrategy(new QueueShuffle());
        else
            queue.setQueueStrategy(new QueueLinear(queue.getCurrentTrackIndex()));
    }
}
