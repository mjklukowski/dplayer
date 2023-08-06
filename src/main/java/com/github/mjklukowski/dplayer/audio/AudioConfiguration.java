package com.github.mjklukowski.dplayer.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.voice.AudioProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AudioConfiguration {

    @Bean
    public AudioPlayerManager createAudioPlayerManager() {
        return new DefaultAudioPlayerManager();
    }

    @Bean
    public AudioPlayer createAudioPlayer(AudioPlayerManager playerManager) {
        playerManager.getConfiguration()
                .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
        return playerManager.createPlayer();
    }

    @Bean
    public AudioProvider createAudioProvider(AudioPlayer player) {
        return new LavaPlayerAudioProvider(player);
    }
}
