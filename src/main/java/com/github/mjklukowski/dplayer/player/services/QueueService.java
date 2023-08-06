package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.player.domain.PlaybackQueue;
import com.github.mjklukowski.dplayer.player.domain.Track;
import discord4j.core.object.entity.Guild;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueueService implements TrackListService {

    private final Map<Guild, PlaybackQueue> queues = new HashMap<>();

    @Override
    public void addTrack(Guild guild, Track track) {
        getQueue(guild).add(track);
    }

    @Override
    public void removeTrack(Guild guild, int trackIndex) {
        getQueue(guild).remove(trackIndex);
    }

    public PlaybackQueue getQueue(Guild guild) {
        if(!queues.containsKey(guild))
            queues.put(guild, new PlaybackQueue());
        return queues.get(guild);
    }
}
