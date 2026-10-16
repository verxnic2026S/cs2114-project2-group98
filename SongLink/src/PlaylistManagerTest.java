import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// -------------------------------------------------------------------------
/**
 * Tests the PlaylistManager class.
 * 
 * @author Veronica Munoz Ospina
 * @version Oct 16, 2026
 */
public class PlaylistManagerTest {
    // ~ Fields ................................................................
    private PlaylistManager manager;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
// ----------------------------------------------------------
    /**
     * Creates new playlist manager before each test
     */
    @Before
    public void setUp() {
        manager = new PlaylistManager();
    }


// ----------------------------------------------------------
    /**
     * Tests that addPlaylist adds a playlists successfully
     */
    @Test
    public void testAddPlaylist() {
        Playlist playlist = new Playlist("Favorites");
        manager.addPlaylist(playlist);
        assertSame(playlist, manager.findByName("Favorites"));
    }


    // ----------------------------------------------------------
    /**
     * Tests that addPlaylist will thrown an exception if the playlist the user
     * is trying to add is null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullPlaylist() {
        manager.addPlaylist(null);
    }


    // ----------------------------------------------------------
    /**
     * Tests that addPlaylist will thrown an exception if the playlist the user
     * is trying to add has the same name as playlists already in the manager
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddSameNamePlaylist() {
        manager.addPlaylist(new Playlist("Favorites"));
        manager.addPlaylist(new Playlist("favorites"));
    }


    // ----------------------------------------------------------
    /**
     * Tests that removePlaylist() will remove most recent playlist.
     */
    @Test
    public void testRemovePlaylist() {
        Playlist favorites = new Playlist("Favorites");
        Playlist fallFavs = new Playlist("Fall Favs");

        manager.addPlaylist(favorites);
        manager.addPlaylist(fallFavs);

        assertSame(fallFavs, manager.removePlaylist());
    }


    // ----------------------------------------------------------
    /**
     * Tests that trying to remove a playlist when the manager is empty will return null
     */
    @Test
    public void testRemovePlaylistWhenEmpty()
    {
        assertNull(manager.removePlaylist());
    }


    // ----------------------------------------------------------
    /**
     * Tests that removePlaylist() will remove playlist based on name.
     */
    @Test
    public void testRemovePlaylistByName() {
        Playlist favorites = new Playlist("Favorites");
        manager.addPlaylist(favorites);

        assertSame(favorites, manager.removePlaylist("Favorites"));
        assertNull(manager.findByName("Favorites"));
    }


    /**
     * Tests removing a playlist that does not exist.
     */
    @Test
    public void testRemovePlaylistNotFound() {
        assertNull(manager.removePlaylist("Missing"));
    }


    /**
     * Tests finding a playlist by its name.
     */
    @Test
    public void testFindByName() {
        Playlist favorites = new Playlist("Favorites");
        manager.addPlaylist(favorites);

        assertSame(favorites, manager.findByName("Favorites"));
    }


    /**
     * Tests that searching with a blank name throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindByNameBlank() {
        manager.findByName("   ");
    }

}
