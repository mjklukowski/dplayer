export interface SearchResult {
    kind: string,
    id?: string,
    url?: string
    data: {
        title: string,
        description?: string,
        channelTitle?: string,
        thumbnails?: {
            [key: string]: Thumbnail
        }
    }
}

interface Thumbnail {
    url: string
}