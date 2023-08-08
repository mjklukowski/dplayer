package com.github.mjklukowski.dplayer.player.domain.queue;

import com.github.mjklukowski.dplayer.player.domain.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QueueShuffleTest {

    QueueShuffle queue;
    List<Track> tracks;

    RandomGenerator random = mock(RandomGenerator.class);

    @BeforeEach
    void setUp() throws MalformedURLException {
        queue = new QueueShuffle(random);

        when(random.nextInt(anyInt(), anyInt()))
                .thenReturn(3, 1, 0, 1, 0);

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
        Assertions.assertEquals("https://wikipedia.org", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://example.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://google.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://youtube.com", queue.next(tracks).url().toString());
        Assertions.assertEquals("https://github.com", queue.next(tracks).url().toString());
    }

    @Test
    void shouldReturnPreviousTrack() {
        Track first = queue.getCurrent(tracks);
        Track second = queue.next(tracks);
        queue.next(tracks);
        Assertions.assertEquals(second, queue.prev(tracks));
        Assertions.assertEquals(first, queue.prev(tracks));
    }

    @Test
    void shouldReturnFirstTrackWhenAtTheBeginning() {
        Track first = queue.getCurrent(tracks);
        Assertions.assertEquals(first, queue.prev(tracks));
    }

    @Test
    void shouldReturnNullWhenReachesEnd() {
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        queue.next(tracks);
        Assertions.assertNull(queue.next(tracks));
    }

    @Nested
    class WithDupeTracks {
        ArrayList<Track> dupeTracks;

        @BeforeEach
        void setUp() throws MalformedURLException {
            when(random.nextInt(anyInt(), anyInt()))
                    .thenReturn(3, 1, 0, 1, 0, 2, 1, 1, 0);

            dupeTracks = new ArrayList<>(tracks);
            dupeTracks.add(new Track(new URL("https://example.com"), "https://example.com", ""));
            dupeTracks.add(new Track(new URL("https://wikipedia.org"), "https://wikipedia.org", ""));
            dupeTracks.add(new Track(new URL("https://wikipedia.org"), "https://wikipedia.org", ""));
            dupeTracks.add(new Track(new URL("https://google.com"), "https://google.com", ""));
        }

        @Test
        void shouldReturnEachTrack() {
            Assertions.assertEquals("https://wikipedia.org", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://example.com", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://google.com", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://youtube.com", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://github.com", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://wikipedia.org", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://wikipedia.org", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://google.com", queue.next(dupeTracks).url().toString());
            Assertions.assertEquals("https://example.com", queue.next(dupeTracks).url().toString());
        }

        @Test
        void currentTrackIndexShouldBeZero() {
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            Assertions.assertEquals(0, queue.getCurrentTrackIndex(dupeTracks));
        }

        @Test
        void currentTrackIndexShouldBeTwo() {
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            Assertions.assertEquals(2, queue.getCurrentTrackIndex(dupeTracks));
        }

        @Test
        void currentTrackIndexShouldBeSeven() {
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            queue.next(dupeTracks);
            Assertions.assertEquals(7, queue.getCurrentTrackIndex(dupeTracks));
        }

    }

}