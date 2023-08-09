import { Channel, Guild } from "../guilds/model"

export interface PlayerStatus {
    guild: Guild | null
    state: PlayerState
    channel: Channel | null
    currentTrack: Track | null
    currentTrackId: number
    shuffle: boolean
}

export enum PlayerState {
    PLAYING = "PLAYING",
    STOPPED = "STOPPED",
    PAUSED = "PAUSED"
}

export interface Track {
    url: string,
    title: string,
    thumbnail: string
}