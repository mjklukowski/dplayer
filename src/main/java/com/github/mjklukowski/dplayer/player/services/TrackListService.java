package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.player.domain.Track;
import discord4j.core.object.entity.Guild;

public interface TrackListService {

    void addTrack(Guild guild, Track track);
    void removeTrack(Guild guild, int trackIndex);
}
