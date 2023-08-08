package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class QueueLinearTest {

    QueueLinear queue;
    List<Track> tracks;

    @BeforeEach
    void setUp() throws MalformedURLException {
        queue = new QueueLinear();
        tracks = List.of(
                new Track(new URL("https://google.com"), "https://google.com", ""),
                new Track(new URL("https://example.com"), "https://example.com", ""),
                new Track(new URL("https://github.com"), "https://github.com", ""),
                new Track(new URL("https://wikipedia.org"), "https://wikipedia.org", ""),
                new Track(new URL("https://youtube.com"), "https://youtube.com", "")
        );
    }

    @Test
    void shouldBeNullWhenEmpty() {
        Assertions.assertNull(queue.getCurrent(List.of()));
        Assertions.assertNull(queue.next(List.of()));
        Assertions.assertNull(queue.prev(List.of()));
    }

    @Test
    void shouldReturnEachTrack() {
        Assertions.assertEquals("https://google.com", queue.getCurrent(tracks).url().toString());
        Assertions.assertEquals("https://example.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://github.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://wikipedia.org", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://youtube.com", queue.next(tracks).url().toString());
    }

    @Test
    void shouldReturnNextThenPrevTrack() {
        Assertions.assertEquals("https://example.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://google.com", queue.prev(tracks).url().toString());
    }

    @Test
    void shouldReturnNullWhenReachesEnd() {
        queue.getCurrent(tracks);
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        Assertions.assertNull(queue.next(tracks));
    }

    @Test
    void shouldReturnFirstTrackWhenAtTheBeginning() {
        Assertions.assertEquals("https://google.com", queue.prev(tracks).url().toString());
    }

    @Test
    void currentTrackIndexShouldBeThree() {
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        Assertions.assertEquals(3, queue.getCurrentTrackIndex(tracks));
    }

    @Test
    void currentTrackIndexShouldBeOne() {
        queue.next(tracks);
        queue.prev(tracks);
        queue.prev(tracks);
        queue.next(tracks);
        Assertions.assertEquals(1, queue.getCurrentTrackIndex(tracks));
    }

    @Test
    void currentTrackIndexAfterRemoveShouldBeTwoThenThree() {
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);

        queue.onRemove(tracks, 0);
        ArrayList<Track> newTracks = new ArrayList<>(tracks);
        newTracks.remove(0);

        Assertions.assertEquals(2, queue.getCurrentTrackIndex(newTracks));
        queue.next(tracks);
        Assertions.assertEquals(3, queue.getCurrentTrackIndex(newTracks));
    }

}