import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SongTest
{
    private Song song;

    @Before
    public void setUp()
    {
        song = new Song("Hello", "Adele");
    }

    @Test
    public void getTitle()
    {
        assertEquals("Hello", song.getTitle());
    }

    @Test
    public void getArtist()
    {
        assertEquals("Adele", song.getArtist());
    }

    @Test
    public void playSong()
    {
        song.playSong();
        assertEquals(1, song.getPlayCount());

        song.playSong();
        assertEquals(2, song.getPlayCount());

        // Playing does not change the song's information.
        assertEquals("Hello", song.getTitle());
        assertEquals("Adele", song.getArtist());
    }

    @Test
    public void getPlayCount()
    {
        assertEquals(0, song.getPlayCount());
    }

    @Test
    public void trimsWhitespace()
    {
        Song trimmed = new Song(" Hello ", " Adele ");

        assertEquals("Hello", trimmed.getTitle());
        assertEquals("Adele", trimmed.getArtist());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTitle()
    {
        new Song(null, "Adele");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTitle()
    {
        new Song("", "Adele");
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankTitle()
    {
        new Song("   ", "Adele");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArtist()
    {
        new Song("Hello", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArtist()
    {
        new Song("Hello", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void blankArtist()
    {
        new Song("Hello", "   ");
    }
}