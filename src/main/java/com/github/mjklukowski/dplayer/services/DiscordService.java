package com.github.mjklukowski.dplayer.services;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscordService {

    private final GatewayDiscordClient client;

    public DiscordService(GatewayDiscordClient client) {
        this.client = client;
    }

    public List<Guild> getGuilds() {
        return client.getGuilds().collectList().block();
    }

    public List<VoiceChannel> getChannels(Snowflake guildId) {
        return client.getGuildChannels(guildId)
                .filter(channel -> channel instanceof VoiceChannel)
                .map(channel -> (VoiceChannel) channel)
                .collectList().block();
    }

}
