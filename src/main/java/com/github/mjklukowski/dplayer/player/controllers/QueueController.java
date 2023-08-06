package com.github.mjklukowski.dplayer.player.controllers;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.domain.Track;
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

    public QueueController(QueueService queueService, DiscordService discordService) {
        this.queueService = queueService;
        this.discordService = discordService;
    }

    @PostMapping("/guild/{guildId}/queue")
    public ResponseEntity<Void> addToQueue(@PathVariable String guildId, @RequestBody Track track) {
        Guild guild = discordService.getGuildById(Snowflake.of(guildId)).orElseThrow();
        this.queueService.addTrack(guild, track);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
