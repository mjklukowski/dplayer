package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.audio.AudioManager;
import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.discord.dto.Channel;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.domain.queue.PlaybackQueue;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueLinear;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueShuffle;
import com.github.mjklukowski.dplayer.player.dto.PlayerState;
import com.github.mjklukowski.dplayer.player.dto.PlayerStatus;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.VoiceConnection;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DiscordPlaybackService implements PlaybackService {

    private final Map<Guild, AudioManager> managers = new HashMap<>();
    private final DiscordService discordService;
    private final QueueService queueService;

    public DiscordPlaybackService(DiscordService discordService, QueueService queueService) {
        this.discordService = discordService;
        this.queueService = queueService;
    }

    private void join(VoiceChannel channel) {
        channel.getVoiceConnection()
                .switchIfEmpty(Mono.defer(
                        () -> getManager(channel)
                                .map(AudioManager::getProvider)
                                .map(provider -> VoiceChannelJoinSpec.builder().provider(provider).build())
                                .flatMap(channel::join)
                    )
                )
                .subscribe();
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
        getManager(channel)
                .subscribe(manager -> manager.playTrack(track));
    }

    @Override
    public void pause(VoiceChannel channel) {
        getManager(channel).subscribe(AudioManager::pauseTrack);
    }

    @Override
    public void resume(VoiceChannel channel) {
        getManager(channel).subscribe(AudioManager::resumeTrack);
    }

    @Override
    public void next(VoiceChannel channel) {
        channel.getGuild()
                .map(queueService::getQueue)
                .map(PlaybackQueue::next)
                .subscribe(next -> next.ifPresentOrElse(track -> play(channel, track), () -> stop(channel)));
    }

    @Override
    public void prev(VoiceChannel channel) {
        channel.getGuild()
                .map(queueService::getQueue)
                .map(PlaybackQueue::prev)
                .subscribe(prev -> prev.ifPresent(track -> play(channel, track)));
    }

    @Override
    public void stop(VoiceChannel channel) {
        getManager(channel).subscribe(AudioManager::stopTrack);
        channel.getVoiceConnection()
                .flatMap(VoiceConnection::disconnect)
                .subscribe();
    }

    @Override
    public void shuffle(VoiceChannel channel, boolean enabled) {
        PlaybackQueue queue = queueService.getQueue(channel.getGuild().block());
        if(enabled)
            queue.setQueueStrategy(new QueueShuffle());
        else
            queue.setQueueStrategy(new QueueLinear(queue.getCurrentTrackIndex()));
    }

    private Mono<AudioManager> getManager(VoiceChannel channel) {
        return channel.getGuild()
                .map(guild -> {
                    if(!managers.containsKey(guild))
                        managers.put(guild, new AudioManager(channel, discordService, this));
                    AudioManager manager = managers.get(guild);
                    manager.setChannel(channel);
                    return manager;
                });
    }

    @Override
    public PlayerStatus getStatus(VoiceChannel channel) {
        Guild discordGuild = channel.getGuild().block();
        com.github.mjklukowski.dplayer.discord.dto.Guild guild = null;
        if(discordGuild != null)
            guild = new com.github.mjklukowski.dplayer.discord.dto.Guild(discordGuild);

        AudioManager manager = managers.get(discordGuild);

        PlayerState state = PlayerState.STOPPED;
        Channel currentChannel = null;
        if(manager != null) {
            state = manager.getState();

            if(state != PlayerState.STOPPED)
                currentChannel = new Channel(manager.getChannel());
        }

        PlaybackQueue queue = queueService.getQueue(discordGuild);

        Track currentTrack = queue.current().orElse(null);
        int currentTrackIndex = queue.getCurrentTrackIndex();
        if(state == PlayerState.STOPPED)
            currentTrack = null;

        return new PlayerStatus(
                guild,
                state,
                currentChannel,
                currentTrack,
                currentTrackIndex,
                queue.getQueueStrategy() instanceof QueueShuffle
        );
    }

}
