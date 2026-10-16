import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UserInputTest
{
    //~ Fields ................................................................
    
    private PlaylistManager manager;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;
    
    //~ Constructors ..........................................................

    public UserInputTest() {
        //:)
    }
    //~Public  Methods ........................................................

    public void setUp() {
        manager = new PlaylistManager();

        originalOut = System.out;
        output = new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));
    }
    
    public void tearDown() {
        System.setOut(originalOut);
    }

    // Simulates user input and runs the menu
    private String runWithInput(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        Scanner scanner = new Scanner(
            new ByteArrayInputStream(bytes));

        UserInput userInput = new UserInput(manager, scanner);

        userInput.run();
        scanner.close();

        return output.toString();
    }

    // Constructor Tests
    /**
    @Test
    public void testConstructor() {
        Scanner scanner = new Scanner("0\n");

        UserInput userInput = new UserInput(manager, scanner);

        assertNotNull(userInput);
        scanner.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullManager() {
        Scanner scanner = new Scanner("0\n");

        try {
            new UserInput(null, scanner);
        }
        finally {
            scanner.close();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullScanner() {
        new UserInput(manager, null);
    }*/
    

    // validate() Tests
    @Test
    public void testValidateNormalString() {
        assertEquals("Favorites",
            UserInput.validate("Favorites"));
    }

    @Test
    public void testValidateTrimSpaces() {
        assertEquals("Favorites",
            UserInput.validate("   Favorites   "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNull() {
        UserInput.validate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateEmpty() {
        UserInput.validate("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateWhitespace() {
        UserInput.validate("     ");
    }

    // Menu Tests
    @Test
    public void testDisplayMenu() {
        String result = runWithInput("0\n");

        assertTrue(result.contains("SongLink Menu"));
        assertTrue(result.contains("Create Playlist"));
        assertTrue(result.contains("Add Song"));
        assertTrue(result.contains("Play Next Song"));
        assertTrue(result.contains("Exit"));
    }

    @Test
    public void testInvalidMenuNumber() {
        String result = runWithInput("15\n0\n");

        assertTrue(result.contains("Invalid choice"));
        assertTrue(result.contains("Goodbye"));
    }

    @Test
    public void testInvalidMenuString() {
        String result = runWithInput("hello\n0\n");

        assertTrue(result.contains("Invalid input"));
        assertTrue(result.contains("Goodbye"));
    }

    @Test
    public void testEmptyMenuInput() {
        String result = runWithInput("\n0\n");

        assertTrue(result.contains("Invalid input"));
    }

    @Test
    public void testExit() {
        String result = runWithInput("0\n");

        assertTrue(result.contains("Goodbye"));
    }

    // Playlist Tests
    @Test
    public void testCreatePlaylist() {
        runWithInput("1\nFavorites\n0\n");

        Playlist playlist = manager.findByName("Favorites");

        assertNotNull(playlist);
        assertEquals("Favorites", playlist.getName());
    }

    @Test
    public void testCreatePlaylistTrimSpaces() {
        runWithInput("1\n   Favorites   \n0\n");

        assertNotNull(manager.findByName("Favorites"));
    }

    @Test
    public void testCreatePlaylistEmptyName() {
        String result = runWithInput(
            "1\n\nFavorites\n0\n");

        assertTrue(result.contains("cannot be null or empty"));
        assertNotNull(manager.findByName("Favorites"));
    }

    @Test
    public void testDuplicatePlaylist() {
        String result = runWithInput(
            "1\nFavorites\n1\nFavorites\n0\n");

        assertTrue(result.contains("already exists"));
        assertNotNull(manager.findByName("Favorites"));
    }

    @Test
    public void testRemovePlaylistByName() {
        manager.addPlaylist(new Playlist("Favorites"));

        runWithInput("2\nFavorites\n0\n");

        assertNull(manager.findByName("Favorites"));
    }

    @Test
    public void testRemoveNonexistentPlaylist() {
        String result = runWithInput("2\nUnknown\n0\n");

        assertTrue(result.contains("Playlist not found"));
    }

    @Test
    public void testRemoveRecentPlaylist() {
        manager.addPlaylist(new Playlist("Favorites"));

        String result = runWithInput("3\n0\n");

        assertTrue(result.contains("Playlist removed"));
        assertNull(manager.findByName("Favorites"));
    }

    // Song Tests
    @Test
    public void testAddSong() {
        Playlist playlist = new Playlist("Favorites");
        manager.addPlaylist(playlist);

        runWithInput(
            "4\nFavorites\nYellow\nColdplay\n0\n");

        Song song = playlist.findSong("Yellow", "Coldplay");

        assertNotNull(song);
        assertEquals("Yellow", song.getTitle());
        assertEquals("Coldplay", song.getArtist());
    }

    @Test
    public void testAddSongToMissingPlaylist() {
        String result = runWithInput(
            "4\nUnknown\n0\n");

        assertTrue(result.contains("Playlist not found"));
    }

    @Test
    public void testRemoveSongByName() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong(new Song("Yellow", "Coldplay"));
        manager.addPlaylist(playlist);

        runWithInput(
            "5\nFavorites\nYellow\nColdplay\n0\n");

        assertNull(playlist.findSong("Yellow", "Coldplay"));
    }

    @Test
    public void testRemoveNonexistentSong() {
        Playlist playlist = new Playlist("Favorites");
        manager.addPlaylist(playlist);

        String result = runWithInput(
            "5\nFavorites\nUnknown\nNobody\n0\n");

        assertTrue(result.contains("Song not found"));
    }

    @Test
    public void testRemoveRecentSong() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong(new Song("Yellow", "Coldplay"));
        manager.addPlaylist(playlist);

        String result = runWithInput(
            "6\nFavorites\n0\n");

        assertTrue(result.contains("Song removed"));
        assertNull(playlist.findSong("Yellow", "Coldplay"));
    }

    @Test
    public void testFindSong() {
        Playlist playlist = new Playlist("Favorites");
        playlist.addSong(new Song("Yellow", "Coldplay"));
        manager.addPlaylist(playlist);

        String result = runWithInput(
            "7\nFavorites\nYellow\nColdplay\n0\n");

        assertTrue(result.contains("Song found"));
        assertTrue(result.contains("Yellow"));
        assertTrue(result.contains("Coldplay"));
    }

    @Test
    public void testFindNonexistentSong() {
        Playlist playlist = new Playlist("Favorites");
        manager.addPlaylist(playlist);

        String result = runWithInput(
            "7\nFavorites\nUnknown\nNobody\n0\n");

        assertTrue(result.contains("Song not found"));
    }

    // Playback Tests

    @Test
    public void testPlayNextSong() {
        Playlist playlist = new Playlist("Favorites");
        Song song = new Song("Yellow", "Coldplay");

        playlist.addSong(song);
        manager.addPlaylist(playlist);

        String result = runWithInput(
            "8\nFavorites\n0\n");

        assertTrue(result.contains("Now Playing"));
        assertEquals(1, song.getPlayCount());
    }

    @Test
    public void testPlayNextSongTwice() {
        Playlist playlist = new Playlist("Favorites");
        Song song = new Song("Yellow", "Coldplay");

        playlist.addSong(song);
        manager.addPlaylist(playlist);

        runWithInput(
            "8\nFavorites\n8\nFavorites\n0\n");

        assertEquals(2, song.getPlayCount());
    }

    @Test
    public void testPlayEmptyPlaylist() {
        manager.addPlaylist(new Playlist("Favorites"));

        String result = runWithInput(
            "8\nFavorites\n0\n");

        assertTrue(result.contains("Playlist is empty"));
    }

    // Most Played Songs Tests

    @Test
    public void testMostPlayedSongs() {
        Playlist playlist = new Playlist("Favorites");

        Song song1 = new Song("Yellow", "Coldplay");
        Song song2 = new Song("Hello", "Adele");

        playlist.addSong(song1);
        playlist.addSong(song2);

        song1.playSong();
        song1.playSong();
        song2.playSong();

        manager.addPlaylist(playlist);

        String result = runWithInput(
            "9\nFavorites\n0\n");

        assertTrue(result.contains("Most Played Songs"));
        assertTrue(result.contains("Yellow"));
        assertTrue(result.contains("Hello"));

        assertTrue(result.indexOf("Yellow")
            < result.indexOf("Hello"));
    }

    @Test
    public void testMostPlayedEmptyPlaylist() {
        manager.addPlaylist(new Playlist("Favorites"));

        String result = runWithInput(
            "9\nFavorites\n0\n");

        assertTrue(result.contains("Playlist is empty"));
    }


}
