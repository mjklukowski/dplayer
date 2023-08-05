package com.github.mjklukowski.dplayer.controller;

import com.github.mjklukowski.dplayer.dto.Channel;
import com.github.mjklukowski.dplayer.dto.Guild;
import com.github.mjklukowski.dplayer.services.DiscordService;
import discord4j.common.util.Snowflake;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GuildController {

    private final DiscordService discordService;

    public GuildController(DiscordService discordService) {
        this.discordService = discordService;
    }

    @GetMapping("/guilds")
    public List<Guild> getGuilds() {
        return discordService.getGuilds()
                .stream()
                .map(guild -> new Guild(guild.getId().asString(), guild.getName()))
                .toList();
    }

    @GetMapping("/guild/{guildId}/channels")
    public List<Channel> getChannels(@PathVariable String guildId) {
        return discordService.getChannels(Snowflake.of(guildId))
                .stream()
                .map(channel -> new Channel(channel.getId().asString(), channel.getName()))
                .toList();
    }

}
