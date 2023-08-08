package com.github.mjklukowski.dplayer.player.controllers;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Playlist;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.dto.PlayerItem;
import com.github.mjklukowski.dplayer.player.services.PlaylistService;
import com.github.mjklukowski.dplayer.player.services.QueueService;
import com.github.mjklukowski.dplayer.player.services.TrackLinkResolverService;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;
    private final QueueService queueService;
    private final DiscordService discordService;
    private final TrackLinkResolverService linkResolverService;

    public PlaylistController(PlaylistService playlistService, QueueService queueService, DiscordService discordService, TrackLinkResolverService linkResolverService) {
        this.playlistService = playlistService;
        this.queueService = queueService;
        this.discordService = discordService;
        this.linkResolverService = linkResolverService;
    }

    @PostMapping("/guild/{guildId}/playlist")
    public ResponseEntity<Playlist> createPlaylist(@PathVariable String guildId, @RequestBody Playlist playlist) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        this.playlistService.addPlaylist(guild, playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @DeleteMapping("/guild/{guildId}/playlist/{playlistId}")
    public ResponseEntity<Void> removePlaylist(@PathVariable String guildId, @PathVariable int playlistId) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        this.playlistService.removePlaylist(guild, playlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/guild/{guildId}/playlist")
    public ResponseEntity<List<Playlist>> getPlaylists(@PathVariable String guildId) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        return ResponseEntity.ok(playlistService.getPlaylists(guild));
    }

    @PostMapping("/guild/{guildId}/playlist/{playlistId}")
    public ResponseEntity<List<Track>> addToPlaylist(@PathVariable String guildId, @PathVariable int playlistId, @RequestBody PlayerItem item) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        List<Track> tracks = linkResolverService.resolve(item.url());

        if(tracks.isEmpty())
            return ResponseEntity.badRequest().build();

        tracks.forEach(track -> this.playlistService.addTrack(guild, playlistId, track));
        return ResponseEntity.status(HttpStatus.CREATED).body(tracks);
    }

    @DeleteMapping("/guild/{guildId}/playlist/{playlistId}/{trackIndex}")
    public ResponseEntity<Void> removeFromPlaylist(@PathVariable String guildId, @PathVariable int playlistId, @PathVariable int trackIndex) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        this.playlistService.removeTrack(guild, playlistId, trackIndex);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/guild/{guildId}/playlist/{playlistId}")
    public ResponseEntity<List<Track>> getPlaylist(@PathVariable String guildId, @PathVariable int playlistId) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        return ResponseEntity.ok(playlistService.getPlaylistById(guild, playlistId).orElseThrow().getTrackList());
    }

    @PostMapping("/guild/{guildId}/playlist/{playlistId}/addToQueue")
    public ResponseEntity<List<Track>> addToQueue(@PathVariable String guildId, @PathVariable int playlistId) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();

        Playlist playlist = playlistService.getPlaylistById(guild, playlistId).orElseThrow();
        queueService.getQueue(guild).add(playlist);

        return ResponseEntity.status(HttpStatus.CREATED).body(playlist.getTrackList());
    }
}
