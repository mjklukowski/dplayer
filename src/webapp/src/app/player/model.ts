export enum PlayerStatus {
    PLAYING = "PLAYING",
    STOPPED = "STOPPED",
    PAUSED = "PAUSED"
}

export interface Track {
    url: string,
    title: string,
    thumbnail: string
}