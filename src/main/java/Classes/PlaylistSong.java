package Classes;

public class PlaylistSong
{
    private int psId;
    private int playlistId;
    private int songId;
    private String songName;
    private String songDuration;
    private String pathOfSong;

    public PlaylistSong(){}

   /* public Classes.PlaylistSong(int psId, int playlistId, int songId,String songName, String pathOfSong) {
        this.psId = psId;
        this.playlistId = playlistId;
        this.songId = songId;
        this.songName = songName;
        this.pathOfSong = pathOfSong;
    }*/

    public int getPsId() {
        return psId;
    }

    public void setPsId(int psId) {
        this.psId = psId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getPathOfSong(String path_name) {
        return pathOfSong;
    }

    public void setPathOfSong(String pathOfSong) {
        this.pathOfSong = pathOfSong;
    }

    @Override
    public String toString() {
        return "PlaylistSong{" +
                "psId=" + psId +
                ", playlistId=" + playlistId +
                ", songId=" + songId +
                ", songName='" + songName + '\'' +
                ", songDuration='" + songDuration + '\'' +
                ", pathOfSong='" + pathOfSong + '\'' +
                '}';
    }
}
