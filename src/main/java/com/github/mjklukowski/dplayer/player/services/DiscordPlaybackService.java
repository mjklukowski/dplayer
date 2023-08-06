package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.audio.TrackScheduler;
import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.domain.queue.PlaybackQueue;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueLinear;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueShuffle;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DiscordPlaybackService implements PlaybackService {

    private final Map<Guild, TrackScheduler> schedulers = new HashMap<>();
    private final DiscordService discordService;
    private final QueueService queueService;
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;
    private final AudioProvider provider;


    public DiscordPlaybackService(DiscordService discordService, QueueService queueService, AudioPlayerManager playerManager, AudioPlayer player, AudioProvider provider) {
        this.discordService = discordService;
        this.queueService = queueService;
        this.playerManager = playerManager;
        this.player = player;
        this.provider = provider;
    }

    private void join(VoiceChannel channel) {
        if(channel.getVoiceConnection().block() != null)
            return;
        channel.join(VoiceChannelJoinSpec.builder().provider(provider).build()).block();
    }

    private TrackScheduler getScheduler(VoiceChannel channel) {
        Guild guild = channel.getGuild().block();
        if(!schedulers.containsKey(guild))
            schedulers.put(guild, new TrackScheduler(channel, discordService, this, playerManager, player));
        TrackScheduler scheduler = schedulers.get(guild);
        scheduler.setChannel(channel);
        return scheduler;
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
        getScheduler(channel).play(track);
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
        player.stopTrack();
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
