package com.github.mjklukowski.dplayer.discord.dto;

public record Guild(String snowflake, String name) {

    public Guild(discord4j.core.object.entity.Guild discordGuild) {
        this(discordGuild.getId().asString(), discordGuild.getName());
    }

}
