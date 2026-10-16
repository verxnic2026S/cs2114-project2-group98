import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SongLinkAppTest
{
    //~ Fields ................................................................
    
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    //~ Constructors ..........................................................

    public SongLinkAppTest() {
        
    }
    //~Public  Methods ........................................................
    
    public void setUp() {
        originalIn = System.in;
        originalOut = System.out;

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // Simulates running SongLinkApp.main()
    private String runApp(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        System.setIn(new ByteArrayInputStream(bytes));

        SongLinkApp.main(new String[0]);

        return output.toString();
    }

    @Test
    public void testMainExit() {
        String result = runApp("0\n");

        assertTrue(result.contains("SongLink Menu"));
        assertTrue(result.contains("Goodbye"));
    }

    @Test
    public void testMainCreatePlaylist() {
        String result = runApp(
            "1\nFavorites\n0\n");

        assertTrue(result.contains(
            "Playlist created successfully"));

        assertTrue(result.contains("Favorites"));
    }

    @Test
    public void testMainInvalidInput() {
        String result = runApp(
            "hello\n0\n");

        assertTrue(result.contains("Invalid input"));
        assertTrue(result.contains("Goodbye"));
    }

    @Test
    public void testMainDuplicatePlaylist() {
        String result = runApp(
            "1\nFavorites\n1\nFavorites\n0\n");

        assertTrue(result.contains("already exists"));
        assertTrue(result.contains("Goodbye"));
    }

    @Test
    public void testMainAddAndFindSong() {
        String input =
            "1\nFavorites\n"
            + "4\nFavorites\nYellow\nColdplay\n"
            + "7\nFavorites\nYellow\nColdplay\n"
            + "0\n";

        String result = runApp(input);

        assertTrue(result.contains(
            "Playlist created successfully"));

        assertTrue(result.contains(
            "Song added successfully"));

        assertTrue(result.contains("Song found"));
    }

    @Test
    public void testMainPlaySong() {
        String input =
            "1\nFavorites\n"
            + "4\nFavorites\nYellow\nColdplay\n"
            + "8\nFavorites\n"
            + "0\n";

        String result = runApp(input);

        assertTrue(result.contains("Now Playing"));
        assertTrue(result.contains("Yellow"));
        assertTrue(result.contains("Play Count: 1"));
    }

    @Test
    public void testMainRemovePlaylist() {
        String input =
            "1\nFavorites\n"
            + "2\nFavorites\n"
            + "0\n";

        String result = runApp(input);

        assertTrue(result.contains("Playlist removed"));
        assertTrue(result.contains("Goodbye"));
    }

}
