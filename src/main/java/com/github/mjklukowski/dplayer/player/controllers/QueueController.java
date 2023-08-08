package com.github.mjklukowski.dplayer.player.controllers;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.player.dto.PlayerItem;
import com.github.mjklukowski.dplayer.player.services.TrackLinkResolverService;
import com.github.mjklukowski.dplayer.player.services.QueueService;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QueueController {

    private final QueueService queueService;
    private final DiscordService discordService;
    private final TrackLinkResolverService linkResolverService;

    public QueueController(QueueService queueService, DiscordService discordService, TrackLinkResolverService trackLinkResolverService) {
        this.queueService = queueService;
        this.discordService = discordService;
        this.linkResolverService = trackLinkResolverService;
    }

    @PostMapping("/guild/{guildId}/queue")
    public ResponseEntity<List<Track>> addToQueue(@PathVariable String guildId, @RequestBody PlayerItem item) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        List<Track> tracks = linkResolverService.resolve(item.url());

        if(tracks.isEmpty())
            return ResponseEntity.badRequest().build();

        tracks.forEach(track -> this.queueService.addTrack(guild, track));
        return ResponseEntity.status(HttpStatus.CREATED).body(tracks);
    }

    @DeleteMapping("/guild/{guildId}/queue/{trackIndex}")
    public ResponseEntity<Void> removeFromQueue(@PathVariable String guildId, @PathVariable int trackIndex) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        this.queueService.removeTrack(guild, trackIndex);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/guild/{guildId}/queue")
    public ResponseEntity<List<Track>> getQueue(@PathVariable String guildId) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        return ResponseEntity.ok(queueService.getQueue(guild).getTrackList());
    }
}
