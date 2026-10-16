// -------------------------------------------------------------------------
/**
 * Manages collection of playlists, allowing users to add, remove, and find
 * playlists by name, implementing a LinkedChain.
 * 
 * @author Veronica Munoz Ospina
 * @version Oct 16, 2026
 */
public class PlaylistManager {
    // ~ Fields ................................................................
    private LinkedChain<Playlist> playlists;

    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new PlaylistManager object.
     */
    public PlaylistManager() {
        playlists = new LinkedChain<Playlist>();
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Adds a playlist to the system
     * 
     * @param playlist
     * @throws IllegalArgumentException
     *             if playlist is null or has the same name
     *             as an already existent playlist.
     */
    public void addPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist cannot be null.");
        }

        if (findByName(playlist.getName()) != null) {
            throw new IllegalArgumentException(
                "Playlist with this name already exists.");
        }

        playlists.add(playlist);
    }


    // ----------------------------------------------------------
    /**
     * Removes most recently added playlist from the system
     * 
     * @return removed playlist
     */
    public Playlist removePlaylist() {
        return playlists.remove();
    }


    // ----------------------------------------------------------
    /**
     * Removes a playlist with the given name.
     *
     * @param name
     *            of the playlist to remove
     * @return the removed playlist, or null if it is not found
     * @throws IllegalArgumentException
     *             if name is null or blank
     */
    public Playlist removePlaylist(String name) {
        Playlist playlist = findByName(name);

        if (playlist == null) {
            return null;
        }

        playlists.remove(playlist);
        return playlist;
    }


    /**
     * Finds a playlist by name, ignoring case.
     *
     * @param playlist
     *            name
     * @return the matching playlist, or null if it is not found
     * @throws IllegalArgumentException
     *             if name is null or blank
     */
    public Playlist findByName(String name) {
        String searchName = UserInput.validate(name);

        Playlist[] allPlaylists = playlists.toArray(new Playlist[playlists
            .size()]);

        for (Playlist playlist : allPlaylists) {
            if (playlist.getName().equalsIgnoreCase(searchName)) {
                return playlist;
            }
        }

        return null;
    }

}
