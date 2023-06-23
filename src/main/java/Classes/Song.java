package Classes;

public class Song
{
    private int id;
    private String songName ;
    private int  artistId ;
    private int genreId ;
    private int albumId ;
    private String songDuration ;
    private String pathName;

    public  Song(){}

    public Song(int id, String songName, int artistId, int genreId, int albumId, String songDuration, String pathName) {
        this.id = id;
        this.songName = songName;
        this.artistId = artistId;
        this.genreId = genreId;
        this.albumId = albumId;
        this.songDuration = songDuration;
        this.pathName = pathName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }


    @Override
    public String toString() {
        return "Classes.Song{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", artistId=" + artistId +
                ", genreId=" + genreId +
                ", albumId=" + albumId +
                ", songDuration='" + songDuration + '\'' +
                ", pathName='" + pathName + '\'' +
                '}';
    }
}
