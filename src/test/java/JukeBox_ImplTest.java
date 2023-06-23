import Classes.Genre;
import Classes.PlayList;
import Classes.Song;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class JukeBox_ImplTest
{
    FetchDataFromDataBase fetch = new FetchDataFromDataBase();
    LogInAndSignUp login = new LogInAndSignUp();
    @Test
    public void loginPass()
    {
        String userName = "nikhil@123";
        String pass = "nikhil@123";
        int i=login.loginUser(userName,pass);
        Assert.assertEquals(1, i);
    }

    @Test
    public void loginFailed()
    {
        String userName = "nikhil@123";
        String pass = "nikhil@13";
        int i=login.loginUser(userName,pass);
        Assert.assertEquals(0, i);
    }

    @Test
    public void testPlayList_ValidPlaylistId() {
        int playlistId = 1;
        String playlistQuery = "SELECT * FROM playlist where playlist_Id =" + playlistId;
        List<PlayList> playList = fetch.PlayList(playlistQuery, playlistId);
        Assert.assertTrue(!playList.isEmpty());
    }

    @Test
    public void testPlayList_InValidPlaylistId() {
        int playlistId = 32;
        String playlistQuery = "SELECT * FROM playlist where playlist_Id =" + playlistId;
        List<PlayList> playList = fetch.PlayList(playlistQuery, playlistId);
        Assert.assertTrue(playList.isEmpty());
    }

    @Test
    public void testGenreList_ValidGenreId()
    {
        int genreId = 101;
        String songPlaylistQuery = "select id,song_name,path_name, song_duration from song where genre_id= "+genreId+";\n";
        List<Song> genreListSong= fetch.showSongs(songPlaylistQuery);
        Assert.assertTrue(!genreListSong.isEmpty());
    }

    @Test
    public void testGenreList_InvalidGenreId()
    {
        int genreId = 11;
        String songPlaylistQuery = "select id,song_name,path_name, song_duration from song where genre_id= "+genreId+";\n";
        List<Song> genreListSong= fetch.showSongs(songPlaylistQuery);
        Assert.assertTrue(genreListSong.isEmpty());
    }

}
