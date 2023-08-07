package com.github.mjklukowski.dplayer.youtube;

import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchListResponse;
import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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

}
