package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.player.domain.queue.PlaybackQueue;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.domain.queue.QueueLinear;
import discord4j.core.object.entity.Guild;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueueService {

    private final Map<Guild, PlaybackQueue> queues = new HashMap<>();

    public void addTrack(Guild guild, Track track) {
        getQueue(guild).add(track);
    }

    public void removeTrack(Guild guild, int trackIndex) {
        getQueue(guild).remove(trackIndex);
    }

    public PlaybackQueue getQueue(Guild guild) {
        if(!queues.containsKey(guild))
            queues.put(guild, new PlaybackQueue(new QueueLinear()));
        return queues.get(guild);
    }
}
