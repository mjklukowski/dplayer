package com.github.mjklukowski.dplayer.player.services;

import com.github.mjklukowski.dplayer.player.domain.Track;
import com.github.mjklukowski.dplayer.youtube.YouTubeService;
import com.github.mjklukowski.dplayer.youtube.dto.video.YouTubeVideo;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TrackLinkResolverService {

    private final YouTubeService youTubeService;

    public TrackLinkResolverService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    public List<Track> resolve(URL url) {
        String id = resolveVideoId(url);

        if(id != null && !id.isEmpty())
            return youTubeService.getVideo(id).map(this::createTrack).collectList().block();

        id = resolvePlaylistId(url);

        if(id != null && !id.isEmpty())
            return youTubeService.getPlaylistVideos(id).map(this::createTrack).collectList().block();

        return List.of();
    }

    @Nullable
    public String resolveVideoId(URL url) {
        Pattern videoLink = Pattern.compile("^https?://(?:www.)?youtube.com/watch\\?v=(.*?)(?:&.*)?$");
        Pattern videoLinkShort = Pattern.compile("^https?://youtu.be/(.*?)(?:\\?.*)?$");

        Matcher matcher = videoLink.matcher(url.toString());
        if(matcher.find())
            return matcher.group(1);

        matcher = videoLinkShort.matcher(url.toString());
        if(matcher.find())
            return matcher.group(1);

        return null;
    }

    @Nullable
    public String resolvePlaylistId(URL url) {
        Pattern playlistLink = Pattern.compile("^https?://(?:www.)?youtube.com/playlist\\?list=(.*?)$");
        Matcher matcher = playlistLink.matcher(url.toString());
        if(matcher.find())
            return matcher.group(1);
        return null;
    }

    private Track createTrack(YouTubeVideo youTubeVideo) {
        try {
            return new Track(
                    new URL("https://youtu.be/" + youTubeVideo.getId()),
                    youTubeVideo.getTitle(),
                    youTubeVideo.getThumbnail()
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
