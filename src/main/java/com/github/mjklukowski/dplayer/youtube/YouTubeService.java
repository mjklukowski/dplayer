package com.github.mjklukowski.dplayer.youtube;

import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchResultSnippet;
import com.github.mjklukowski.dplayer.youtube.dto.playlist.YouTubePlaylistItem;
import com.github.mjklukowski.dplayer.youtube.dto.playlist.YouTubePlaylistResponse;
import com.github.mjklukowski.dplayer.youtube.dto.search.YouTubeSearchListResponse;
import com.github.mjklukowski.dplayer.youtube.dto.search.YouTubeSearchResult;
import com.github.mjklukowski.dplayer.youtube.dto.video.YouTubeVideo;
import com.github.mjklukowski.dplayer.youtube.dto.video.YouTubeVideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class YouTubeService {

    @Value("${youtube.key}")
    private String API_KEY;

    private final WebClient client;

    public YouTubeService(WebClient client) {
        this.client = client;
    }

    public Mono<List<YouTubeSearchResult>> search(String query) {
        return client.get().uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("type", "video,playlist")
                        .queryParam( "q", URLEncoder.encode(query, StandardCharsets.UTF_8))
                        .queryParam("key", API_KEY)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().isError())
                        return Mono.empty();

                    return response.bodyToMono(YouTubeSearchListResponse.class);
                })
                .map(YouTubeSearchListResponse::items);
    }

    public Flux<YouTubeVideo> getVideo(String videoId) {
        return client.get().uri(uriBuilder -> uriBuilder
                        .path("/videos")
                        .queryParam("part", "snippet,contentDetails,statistics")
                        .queryParam("id", videoId)
                        .queryParam("key", API_KEY)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().isError())
                        return Mono.empty();

                    return response.bodyToMono(YouTubeVideoListResponse.class);
                })
                .flatMapIterable(YouTubeVideoListResponse::items)
                .take(1);
    }

    public Mono<YouTubeVideo> getVideo(YouTubeSearchResult searchResult) {
        if(!searchResult.isVideo())
            return Mono.empty();

        YouTubeVideo youTubeVideo = new YouTubeVideo();
        youTubeVideo.setData(searchResult.getData());

        return Mono.just(youTubeVideo);
    }

    public Flux<YouTubePlaylistItem> getPlaylistVideoItems(String playlistId, String nextPageToken) {
        return client.get().uri(uriBuilder -> uriBuilder
                        .path("/playlistItems")
                        .queryParam("part", "snippet")
                        .queryParam("playlistId", playlistId)
                        .queryParam("pageToken", nextPageToken)
                        .queryParam("maxResults", 50)
                        .queryParam("key", API_KEY)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().isError())
                        return Mono.empty();

                    return response.bodyToMono(YouTubePlaylistResponse.class);
                })
                .flatMapMany(response -> {
                    if(response.nextPageToken() != null)
                        return Flux.concat(Flux.fromIterable(response.items()), getPlaylistVideoItems(playlistId, response.nextPageToken()));
                    return Flux.fromIterable(response.items());
                });
    }

    public Flux<YouTubeVideo> getPlaylistVideos(String playlistId) {
        return getPlaylistVideoItems(playlistId, null)
                .map(item -> {
                    YouTubeVideo video = new YouTubeVideo();
                    video.setData(new YouTubeSearchResultSnippet(
                            item.getData().title(),
                            item.getData().description(),
                            item.getData().thumbnails(),
                            item.getData().channelTitle()
                    ));
                    video.setId(item.getData().resourceId().get("videoId"));
                    return video;
                });
    }

}
