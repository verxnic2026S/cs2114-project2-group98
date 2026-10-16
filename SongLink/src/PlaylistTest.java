import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaylistTest
{
    private Playlist playlist;
    private Song first;
    private Song second;
    private Song third;

    @Before
    public void setUp()
    {
        playlist = new Playlist("Favorites");
        first = new Song("Hello", "Adele");
        second = new Song("Yellow", "Coldplay");
        third = new Song("Hello", "Lionel Richie");
    }

    @Test
    public void getName()
    {
        assertEquals("Favorites", playlist.getName());
    }

    @Test
    public void addSong()
    {
        playlist.addSong(first);
        playlist.addSong(second);

        assertSame(first, playlist.findSong("Hello", "Adele"));
        assertSame(second, playlist.findSong("Yellow", "Coldplay"));

        // New songs are added at the front.
        assertSame(second, playlist.playNextSong());
        assertSame(first, playlist.playNextSong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullSong()
    {
        playlist.addSong(null);
    }

    @Test
    public void removeSong()
    {
        assertNull(playlist.removeSong());

        playlist.addSong(first);
        playlist.addSong(second);

        assertSame(second, playlist.removeSong());
        assertNull(playlist.findSong("Yellow", "Coldplay"));
        assertSame(first, playlist.removeSong());
        assertNull(playlist.removeSong());
    }

    @Test
    public void testRemoveSong()
    {
        playlist.addSong(first);
        playlist.addSong(second);
        playlist.addSong(third);

        // Remove a song from the middle.
        assertSame(second,
                playlist.removeSong("Yellow", "Coldplay"));
        assertNull(playlist.findSong("Yellow", "Coldplay"));

        // Same title, different artist must remain.
        assertSame(first,
                playlist.removeSong("Hello", "Adele"));
        assertSame(third,
                playlist.findSong("Hello", "Lionel Richie"));

        assertNull(playlist.removeSong("Missing", "Unknown"));
        assertSame(third, playlist.removeSong());
        assertNull(playlist.removeSong("Hello", "Adele"));
    }

    @Test
    public void findSong()
    {
        assertNull(playlist.findSong("Hello", "Adele"));

        playlist.addSong(first);
        playlist.addSong(second);
        playlist.addSong(third);

        assertSame(first, playlist.findSong("Hello", "Adele"));
        assertSame(second, playlist.findSong("Yellow", "Coldplay"));
        assertSame(third,
                playlist.findSong("Hello", "Lionel Richie"));

        assertSame(first, playlist.findSong("HELLO", "adele"));
        assertSame(first, playlist.findSong(" Hello ", " Adele "));

        assertNull(playlist.findSong("Missing", "Adele"));
        assertNull(playlist.findSong("Hello", "Unknown"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSongNullTitle()
    {
        playlist.findSong(null, "Adele");
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSongBlankArtist()
    {
        playlist.findSong("Hello", "   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSongBlankTitle()
    {
        playlist.removeSong("   ", "Adele");
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeSongNullArtist()
    {
        playlist.removeSong("Hello", null);
    }

    @Test
    public void playNextSong()
    {
        assertNull(playlist.playNextSong());

        playlist.addSong(first);
        playlist.addSong(second);

        assertSame(second, playlist.playNextSong());
        assertSame(first, playlist.playNextSong());

        // Playback wraps to the beginning.
        assertSame(second, playlist.playNextSong());

        assertEquals(2, second.getPlayCount());
        assertEquals(1, first.getPlayCount());
    }

    @Test
    public void playSingleSong()
    {
        playlist.addSong(first);

        assertSame(first, playlist.playNextSong());
        assertSame(first, playlist.playNextSong());
        assertEquals(2, first.getPlayCount());
    }

    @Test
    public void addingResetsPlayback()
    {
        playlist.addSong(first);
        playlist.addSong(second);
        playlist.playNextSong();

        playlist.addSong(third);

        assertSame(third, playlist.playNextSong());
    }

    @Test
    public void removingFirstResetsPlayback()
    {
        playlist.addSong(first);
        playlist.addSong(second);
        playlist.addSong(third);
        playlist.playNextSong();

        playlist.removeSong();

        assertSame(second, playlist.playNextSong());
    }

    @Test
    public void removingByNameResetsPlayback()
    {
        playlist.addSong(first);
        playlist.addSong(second);
        playlist.addSong(third);
        playlist.playNextSong();

        playlist.removeSong("Hello", "Adele");

        assertSame(third, playlist.playNextSong());
    }

    @Test
    public void getMostPlayedSongs()
    {
        assertEquals(0, playlist.getMostPlayedSongs().length);

        playlist.addSong(first);
        playlist.addSong(second);
        playlist.addSong(third);

        // Order: third, second, first.
        playlist.playNextSong();
        playlist.playNextSong();
        playlist.playNextSong();
        playlist.playNextSong();
        playlist.playNextSong();
        playlist.playNextSong();
        playlist.playNextSong();

        // Counts: third = 3, second = 2, first = 2.
        playlist.removeSong("Yellow", "Coldplay");
        playlist.addSong(second);
        playlist.playNextSong();

        // Counts: second = 3, third = 3, first = 2.
        playlist.playNextSong();

        // Counts: third = 4, second = 3, first = 2.
        Song[] result = playlist.getMostPlayedSongs();

        assertEquals(3, result.length);
        assertSame(third, result[0]);
        assertSame(second, result[1]);
        assertSame(first, result[2]);

        // Sorting must not change playback or play counts.
        assertSame(first, playlist.playNextSong());
        assertEquals(4, third.getPlayCount());
        assertEquals(3, second.getPlayCount());
        assertEquals(3, first.getPlayCount());
    }

    @Test
    public void mostPlayedWithTies()
    {
        playlist.addSong(first);
        playlist.addSong(second);

        Song[] result = playlist.getMostPlayedSongs();

        assertEquals(2, result.length);
        assertTrue(result[0] == first || result[0] == second);
        assertTrue(result[1] == first || result[1] == second);
        assertNotSame(result[0], result[1]);
    }
}