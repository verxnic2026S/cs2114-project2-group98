public class Song {
    private final String title;
    private final String artist;
    private int playCount;

    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
    }

    public String getTitle(){
        return this.title;
    }

    public String getArtist(){
        return this.artist;
    }

    public void playSong(){
        this.playCount++;
    }

    public int getPlayCount(){
       return this.playCount;
    }
}
