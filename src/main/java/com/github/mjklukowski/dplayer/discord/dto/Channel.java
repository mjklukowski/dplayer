package com.github.mjklukowski.dplayer.discord.dto;

public record Channel(String snowflake, String name) {
    public Channel(discord4j.core.object.entity.channel.VoiceChannel discordChannel) {
        this(discordChannel.getId().asString(), discordChannel.getName());
    }
}
