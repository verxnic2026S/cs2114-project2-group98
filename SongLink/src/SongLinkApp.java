import java.util.Scanner;

public class SongLinkApp
{
    public static void main(String[] args) {
        
        PlaylistManager manager = new PlaylistManager();
        Scanner scanner = new Scanner(System.in);
        UserInput userInput = new UserInput(manager, scanner);
        
        try {
            userInput.run();
        }
        finally {
            scanner.close();
        }
    }

}
