package com.github.mjklukowski.dplayer.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DiscordService {

    private final GatewayDiscordClient client;

    public DiscordService(GatewayDiscordClient client) {
        this.client = client;
    }

    public List<Guild> getGuilds() {
        return client.getGuilds().collectList().block();
    }

    public Optional<Guild> getGuildById(Snowflake guildId) {
        return Optional.ofNullable(client.getGuildById(guildId).block());
    }

    public List<VoiceChannel> getChannels(Snowflake guildId) {
        return client.getGuildChannels(guildId)
                .filter(VoiceChannel.class::isInstance)
                .map(VoiceChannel.class::cast)
                .sort(Comparator.comparingInt(DiscordService::getVoiceChannelPosition))
                .collectList().block();
    }

    private static Integer getVoiceChannelPosition(VoiceChannel voiceChannel) {
        return Optional.ofNullable(voiceChannel.getPosition().block()).orElse(Integer.MAX_VALUE);
    }

    public Optional<VoiceChannel> getChannelById(Snowflake channelId) {
        return Optional.ofNullable(client.getChannelById(channelId)
                .filter(VoiceChannel.class::isInstance)
                .map(VoiceChannel.class::cast)
                .block());
    }

    private Optional<TextChannel> getBotChannel(Guild guild) {
        return Optional.ofNullable((TextChannel) guild.getChannels()
                .filter(ch -> ch.getName().equals("dplayer"))
                .blockFirst());
    }

    public void sendMessage(Guild guild, String message) {
        getBotChannel(guild).ifPresent(channel -> channel.createMessage(message).block());
    }

}
