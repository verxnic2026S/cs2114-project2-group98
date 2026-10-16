import java.util.PriorityQueue;

public class Playlist {

    private final String name;
    private LinkedChain<Song> songs;
    private int nextSongIndex;
    public Playlist(String name){
        this.name = UserInput.validate(name);
        this.songs = new LinkedChain<>();
        nextSongIndex = 0;
    }

    public String getName()
    {
        return name;
    }

    public void addSong(Song song){
        songs.add(song);
        nextSongIndex = 0;
    }


    public Song removeSong(){
        Song removed = songs.remove();
        nextSongIndex = 0;
        return removed;
    }

    public Song removeSong(String title, String artist){
        Song song = findSong(title, artist);
        if(song == null){
            return null;
        }
        songs.remove(song);
        nextSongIndex = 0;
        return song;
    }

    public Song findSong(String title, String artist){
        title = UserInput.validate(title);
        artist = UserInput.validate(artist);
        for(int i = 0; i < songs.size(); i++){
            Song song = songs.get(i);

            if(song.getTitle().equalsIgnoreCase(title) && song.getArtist().equalsIgnoreCase(artist)){
                return song;
            }
        }
        return null;
    }

    public Song playNextSong(){
        if (songs.isEmpty())
        {
            return null;
        }
        Song current = songs.get(nextSongIndex);
        current.playSong();
        nextSongIndex = (nextSongIndex + 1) % songs.size();
        return current;
    }

    public Song[] getMostPlayedSongs(){

        PriorityQueue<Song> queue = new PriorityQueue<>((a,b) -> Integer.compare(b.getPlayCount(), a.getPlayCount()));

        for(int i = 0; i < songs.size(); i++){
            queue.add(songs.get(i));
        }
        Song[] res = new Song[songs.size()];
        for(int i = 0; i < res.length; i++){
            res[i] = queue.poll();
        }

        return res;
    }
}
