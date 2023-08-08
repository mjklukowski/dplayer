package com.github.mjklukowski.dplayer.youtube;

import com.github.mjklukowski.dplayer.youtube.dto.search.YouTubeSearchResult;
import com.github.mjklukowski.dplayer.youtube.dto.video.YouTubeVideo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {

    private final YouTubeService youTubeService;

    public YouTubeController(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<YouTubeSearchResult>> search(@RequestParam(defaultValue = "") String query) {
        Mono<List<YouTubeSearchResult>> search = youTubeService.search(query);
        return ResponseEntity.ok(search.block());
    }

    @GetMapping("/video")
    public ResponseEntity<YouTubeVideo> getVideo(@RequestParam(defaultValue = "") String videoId) {
        Flux<YouTubeVideo> video = youTubeService.getVideo(videoId);
        return ResponseEntity.ok(video.blockFirst());
    }

    @GetMapping("/playlist")
    public ResponseEntity<List<YouTubeVideo>> getPlaylistVideos(@RequestParam(defaultValue = "") String playlistId) {
        Flux<YouTubeVideo> video = youTubeService.getPlaylistVideos(playlistId);
        return ResponseEntity.ok(video.collectList().block());
    }

}
