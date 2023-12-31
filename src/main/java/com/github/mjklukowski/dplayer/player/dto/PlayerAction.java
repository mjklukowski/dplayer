package com.github.mjklukowski.dplayer.player.dto;

public record PlayerAction(Action action, Integer trackId) {
    public enum Action {
        PLAY,
        PAUSE,
        RESUME,
        STOP,
        NEXT,
        PREV,
        SHUFFLE_ON,
        SHUFFLE_OFF
    }
}
