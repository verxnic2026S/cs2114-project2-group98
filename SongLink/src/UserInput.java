import java.util.Scanner;

public class UserInput
{
    //~ Fields ................................................................
    
    private PlaylistManager manager;
    private Scanner scanner;
    
    //~ Constructors ..........................................................
    
    /**
     * Creates a UserInput object.
     *
     * @param manager the playlist manager
     * @param scanner the scanner for user input
     */
    public UserInput(PlaylistManager manager, Scanner scanner) {
        if (manager == null || scanner == null) {
            throw new IllegalArgumentException("Manager and Scanner"
                + "cannot be null.");
        }
        
        this.manager = manager;
        this.scanner = scanner;
    }
    //~Public  Methods ........................................................
    
    /**
     * Validates a string.
     * Removes leading and trailing whitespace.
     *
     * @param value the string to validate
     * @return the validated string
     */
    public static String validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Input cannot be null or empty.");
        }

        return value.trim();
    }
    
    /**
     * Displays the main menu.
     */
    private void displayMenu() {
        System.out.println();
        System.out.println("===== Playlist Menu =====");
        System.out.println("1. Create Playlist");
        System.out.println("2. Remove Playlist by Name");
        System.out.println("3. Remove Most Recently Added Playlist");
        System.out.println("4. Add Song");
        System.out.println("5. Remove Song by Title and Artist");
        System.out.println("6. Remove Most Recently Added Song");
        System.out.println("7. Find Song");
        System.out.println("8. Play Next Song");
        System.out.println("9. Show Most Played Songs");
        System.out.println("0. Exit");
    }
    
    /**
     * Reads and validates the user's menu choice.
     *
     * @return a valid menu option
     */
    private int readChoice() {
        while (true) {
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input.trim());

                if (choice >= 0 && choice <= 9) {
                    return choice;
                }
                else {
                    displayMessage(
                        "Invalid choice. Please enter 0-9.");
                }
            }
            catch (NumberFormatException e) {
                displayMessage(
                    "Invalid input. Please enter an integer.");
            }
        }
    }
    
    /**
     * Reads and validates a string from the user.
     *
     * @param prompt the message displayed to the user
     * @return a validated string
     */
    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine();

            try {
                return validate(input);
            }
            catch (IllegalArgumentException e) {
                displayMessage(e.getMessage());
            }
        }
    }
    
    /**
     * Displays a message to the user.
     *
     * @param message the message to display
     */
    private void displayMessage(String message) {
        System.out.println(message);
    }


    /**
     * Prompts the user to select a playlist.
     *
     * @return the selected playlist,
     *         or null if the playlist does not exist
     */
    private Playlist selectPlaylist() {
        String name = readString("Enter playlist name: ");
        
        Playlist playlist = manager.findByName(name);

        if (playlist == null) {
            displayMessage("Playlist not found.");
        }

        return playlist;
    }
    
    /**
     * Handles the user's menu choice.
     *
     * @param choice the selected menu option
     */
    private void handleChoice(int choice) {

        switch (choice) {

            case 1: {
                // Create Playlist

                String name = readString(
                    "Enter new playlist name: ");

                Playlist playlist = new Playlist(name);

                manager.addPlaylist(playlist);

                displayMessage(
                    "Playlist created successfully: "
                    + playlist.getName());

                break;
            }


            case 2: {
                // Remove Playlist by Name

                String name = readString(
                    "Enter playlist name to remove: ");

                Playlist removed =
                    manager.removePlaylist(name);

                if (removed == null) {
                    displayMessage("Playlist not found.");
                }
                else {
                    displayMessage(
                        "Playlist removed: "
                        + removed.getName());
                }

                break;
            }


            case 3: {
                // Remove Most Recently Added Playlist

                Playlist removed = manager.removePlaylist();

                if (removed == null) {
                    displayMessage(
                        "No playlists available to remove.");
                }
                else {
                    displayMessage(
                        "Playlist removed: "
                        + removed.getName());
                }

                break;
            }


            case 4: {
                // Add Song

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                String title = readString(
                    "Enter song title: ");

                String artist = readString(
                    "Enter artist name: ");

                Song song = new Song(title, artist);

                playlist.addSong(song);

                displayMessage(
                    "Song added successfully: "
                    + song.getTitle()
                    + " - "
                    + song.getArtist());

                break;
            }


            case 5: {
                // Remove Song by Title and Artist

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                String title = readString(
                    "Enter song title: ");

                String artist = readString(
                    "Enter artist name: ");

                Song removed =
                    playlist.removeSong(title, artist);

                if (removed == null) {
                    displayMessage("Song not found.");
                }
                else {
                    displayMessage(
                        "Song removed: "
                        + removed.getTitle()
                        + " - "
                        + removed.getArtist());
                }

                break;
            }


            case 6: {
                // Remove Most Recently Added Song

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                Song removed = playlist.removeSong();

                if (removed == null) {
                    displayMessage(
                        "Playlist is empty. No song to remove.");
                }
                else {
                    displayMessage(
                        "Song removed: "
                        + removed.getTitle()
                        + " - "
                        + removed.getArtist());
                }

                break;
            }


            case 7: {
                // Find Song

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                String title = readString(
                    "Enter song title: ");

                String artist = readString(
                    "Enter artist name: ");

                Song song = playlist.findSong(title, artist);

                if (song == null) {
                    displayMessage("Song not found.");
                }
                else {
                    displayMessage("Song found!");
                    displayMessage(
                        "Title: " + song.getTitle());
                    displayMessage(
                        "Artist: " + song.getArtist());
                    displayMessage(
                        "Play Count: "
                        + song.getPlayCount());
                }

                break;
            }


            case 8: {
                // Play Next Song

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                Song song = playlist.playNextSong();

                if (song == null) {
                    displayMessage(
                        "Playlist is empty. Cannot play song.");
                }
                else {
                    displayMessage(
                        "Now Playing: "
                        + song.getTitle()
                        + " - "
                        + song.getArtist());

                    displayMessage(
                        "Play Count: "
                        + song.getPlayCount());
                }

                break;
            }


            case 9: {
                // Display Most Played Songs

                Playlist playlist = selectPlaylist();

                if (playlist == null) {
                    break;
                }

                Song[] songs =
                    playlist.getMostPlayedSongs();

                if (songs.length == 0) {
                    displayMessage(
                        "Playlist is empty. No songs to display.");
                    break;
                }

                displayMessage(
                    "===== Most Played Songs =====");

                for (int i = 0; i < songs.length; i++) {
                    Song song = songs[i];

                    System.out.println(
                        (i + 1) + ". "
                        + song.getTitle()
                        + " - "
                        + song.getArtist()
                        + " | Play Count: "
                        + song.getPlayCount());
                }

                break;
            }


            case 0: {
                // Exit Application

                displayMessage(
                    "Thank you for using SongLink. Goodbye!");

                break;
            }


            default: {
                displayMessage("Invalid menu choice.");

                break;
            }
        }
    }

    /**
     * Starts the application.
     * Repeatedly displays the menu and processes
     * user input until the user chooses to exit.
     */
    public void run() {

        int choice;

        do {
            displayMenu();

            choice = readChoice();

            try {
                handleChoice(choice);
            }
            catch (IllegalArgumentException e) {
                displayMessage(
                    "Error: " + e.getMessage());
            }

        } while (choice != 0);
    }
}


