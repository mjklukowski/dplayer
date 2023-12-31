package com.github.mjklukowski.dplayer.player.controllers;

import com.github.mjklukowski.dplayer.discord.DiscordService;
import com.github.mjklukowski.dplayer.player.dto.PlayerAction;
import com.github.mjklukowski.dplayer.player.dto.PlayerStatus;
import com.github.mjklukowski.dplayer.player.services.PlaybackService;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.VoiceChannel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlaybackController {

    private final PlaybackService playbackService;
    private final DiscordService discordService;

    public PlaybackController(PlaybackService playbackService, DiscordService discordService) {
        this.playbackService = playbackService;
        this.discordService = discordService;
    }

    @PutMapping("/player/{channelId}")
    public ResponseEntity<PlayerStatus> setPlayerAction(@PathVariable String channelId, @RequestBody PlayerAction action) {
        VoiceChannel channel = discordService.getChannelById(Snowflake.of(channelId)).orElseThrow();

        switch (action.action()) {
            case PLAY -> {
                if(action.trackId() == null)
                    playbackService.play(channel);
                else
                    playbackService.play(channel, action.trackId());
            }
            case PAUSE -> playbackService.pause(channel);
            case RESUME -> playbackService.resume(channel);
            case STOP -> playbackService.stop(channel);
            case NEXT -> playbackService.next(channel);
            case PREV -> playbackService.prev(channel);
            case SHUFFLE_ON -> playbackService.shuffle(channel, true);
            case SHUFFLE_OFF -> playbackService.shuffle(channel, false);
        }

        return ResponseEntity.ok(playbackService.getStatus(channel));
    }

    @GetMapping("/player/{channelId}")
    public ResponseEntity<PlayerStatus> getPlayerStatus(@PathVariable String channelId) {
        VoiceChannel channel = discordService.getChannelById(Snowflake.of(channelId)).orElseThrow();
        return ResponseEntity.ok(playbackService.getStatus(channel));
    }

}
