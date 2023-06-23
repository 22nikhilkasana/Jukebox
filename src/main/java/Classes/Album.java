package Classes;

public class Album
{
    int albumId;
    String albumName;

    public Album() {}

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Classes.Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                '}';
    }
}
