package com.github.mjklukowski.dplayer.youtube;

import com.github.mjklukowski.dplayer.youtube.dto.YouTubeSearchResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController()
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

}
