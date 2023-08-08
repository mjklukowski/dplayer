package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.player.domain.Playlist;
import com.github.mjklukowski.dplayer.player.domain.Track;
import discord4j.core.object.entity.Guild;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaylistService {

    private final Map<Guild, List<Playlist>> playlists = new HashMap<>();

    public void addPlaylist(Guild guild, Playlist playlist) {
        getPlaylists(guild).add(playlist);
    }

    public void removePlaylist(Guild guild, int playlistId) {
        getPlaylists(guild).remove(playlistId);
    }

    public void addTrack(Guild guild, int playlistId, Track track) {
        getPlaylistById(guild, playlistId).ifPresent(playlist -> playlist.add(track));
    }

    public void removeTrack(Guild guild, int playlistId, int trackIndex) {
        getPlaylistById(guild, playlistId).ifPresent(playlist -> playlist.remove(trackIndex));
    }

    public List<Playlist> getPlaylists(Guild guild) {
        if(!playlists.containsKey(guild))
            playlists.put(guild, new ArrayList<>());
        return playlists.get(guild);
    }

    public Optional<Playlist> getPlaylistById(Guild guild, int playlistId) {
        return Optional.ofNullable(getPlaylists(guild).get(playlistId));
    }
}
