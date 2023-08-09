package com.github.mjklukowski.dplayer.player.dto;

import com.github.mjklukowski.dplayer.discord.dto.Channel;
import com.github.mjklukowski.dplayer.discord.dto.Guild;
import com.github.mjklukowski.dplayer.player.domain.Track;

public record PlayerStatus(
        Guild guild,
        PlayerState state,
        Channel channel,
        Track currentTrack,
        boolean shuffle
) {
}
