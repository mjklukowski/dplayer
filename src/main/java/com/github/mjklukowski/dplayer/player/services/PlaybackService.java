package com.github.mjklukowski.dplayer.player.services;

import discord4j.core.object.entity.channel.VoiceChannel;

public interface PlaybackService {
    void play(VoiceChannel channel);
    void play(VoiceChannel channel, int trackIndex);
    void pause(VoiceChannel channel);
    void next(VoiceChannel channel);
    void prev(VoiceChannel channel);
    void stop(VoiceChannel channel);
}
