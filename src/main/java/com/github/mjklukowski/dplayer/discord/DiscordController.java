package com.github.mjklukowski.dplayer.discord;

import com.github.mjklukowski.dplayer.discord.dto.Channel;
import com.github.mjklukowski.dplayer.discord.dto.Guild;
import discord4j.common.util.Snowflake;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscordController {

    private final DiscordService discordService;

    public DiscordController(DiscordService discordService) {
        this.discordService = discordService;
    }

    @GetMapping("/guilds")
    public List<Guild> getGuilds() {
        return discordService.getGuilds()
                .stream()
                .map(guild -> new Guild(guild.getId().asString(), guild.getName()))
                .toList();
    }

    @GetMapping("/guild/{guildId}")
    public Guild getGuild(@PathVariable String guildId) {
        return discordService.getGuildById(Snowflake.of(guildId))
                .map(guild -> new Guild(guild.getId().asString(), guild.getName()))
                .orElseThrow();
    }

    @GetMapping("/guild/{guildId}/channels")
    public List<Channel> getChannels(@PathVariable String guildId) {
        return discordService.getChannels(Snowflake.of(guildId))
                .stream()
                .map(channel -> new Channel(channel.getId().asString(), channel.getName()))
                .toList();
    }

}
