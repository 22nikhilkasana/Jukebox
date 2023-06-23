import Classes.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FetchDataFromDataBase extends Connection_DB
{
    Scanner sc = new Scanner(System.in);
    Connection con;
    ResultSet rs;

    public void playSong(String details) {
        con = getConnection();
        try {
            PreparedStatement Stmt = con.prepareStatement(details);
            rs = Stmt.executeQuery();
            int id = 0;
            String path = "";
            String name = "";
            String duration = "";

            System.out.println("Please enter the id of the song that you want to play");
            int songId = sc.nextInt();

            // these ids and path are to play song on the base of song id
            List<Integer> ids = new ArrayList<>();
            List<String> paths = new ArrayList<>();

            // these lists are to print playing song details
            List<String> names = new ArrayList<>();
            List<String> durations = new ArrayList<>();

            while (rs.next()) {
                name = rs.getString("song_name");
                duration= rs.getString("song_duration");
                id = rs.getInt("id");
                path = rs.getString("path_name");
                ids.add(id);
                paths.add(path);
                names.add(name);
                durations.add(duration);
            }

            int index = ids.indexOf(songId);
            if (index != -1) {
                Clip clip = AudioSystem.getClip();
                String response = "";

                while (!response.equals("Q")) {
                    name = names.get(index);
                    duration = durations.get(index);
                    if (!clip.isOpen()) {
                        File file = new File(paths.get(index));
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        clip.open(audioStream);
                    }

                    System.out.println("P = play, S = Stop, R = Reset, L = Loop, N = Next, B = Back, Q = Quit");
                    System.out.print("Enter your choice: ");
                    response = sc.next();
                    response = response.toUpperCase();

                    switch (response) {
                        case ("P"):
                            System.out.println("+-----------------------------------------------------------------------+");
                            System.out.println("| Song Name     :                |"+ name);
                            System.out.println("| Song Duration :                |"+ duration);
                            System.out.println("+-----------------------------------------------------------------------+");
                            clip.start();
                            break;
                        case ("S"):
                            clip.stop();
                            break;
                        case ("R"):
                            clip.setMicrosecondPosition(0);
                            break;
                        case ("L"):
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            break;
                        case ("N"):
                            clip.stop();
                            clip.close();
                            if (index < ids.size() - 1) {
                                index++;
                            } else {
                                System.out.println("No more songs to play");
                                response = "Q";
                            }
                            break;
                        case ("B"):
                            clip.stop();
                            clip.close();
                            if (index > 0) {
                                index--;
                            } else {
                                System.out.println("This is the first song, cannot play previous");
                            }
                            break;
                        case ("Q"):
                            clip.stop();
                            clip.close();
                            break;
                        default:
                            System.out.println("Not a valid response");
                    }
                }
            } else {
                System.out.println("The entered song id is not present in the database");
            }
        } catch (Exception e) {
            System.out.println("Problem is " + e);
        }
    }

    // this method is used to get all songs present in the list
    public List<Song> showSongList() {
        List<Song> songList = new ArrayList<>();
        con = getConnection();
        try {
            String allSongsQuery = "SELECT * FROM song";
            PreparedStatement stmt = con.prepareStatement(allSongsQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Song song = new Song();
                song.setId(rs.getInt("id"));
                song.setSongName(rs.getString("song_name"));
                song.setArtistId(rs.getInt("artist_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setSongDuration(rs.getString("song_duration"));
                songList.add(song);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return songList;
    }

    public void printSongTable(List<Song> songList) {
        System.out.println("+----*------------------*---------*------------*----------*----------------*----------+");
        System.out.println("| ID | Song Name                  | Artist ID  | Genre ID | Album ID | Song Duration  |");
        System.out.println("+----+------------------+---------+------------+----------+----------------+----------+");
        for (Song song : songList) {
            System.out.format("| %2d | %-26s | %-10d | %-8d | %-8d | %-14s |\n",
                    song.getId(), song.getSongName(), song.getArtistId(), song.getGenreId(),
                    song.getAlbumId(), song.getSongDuration());
        }
        System.out.println("+----+------------------+----------+----------+----------+----------------+-----------+");
    }

    //this method is used to print the playlist

    public List<PlayList> PlayList(String playlistQuery, int uId) {
        List<PlayList> playList = new ArrayList<>();
        con = getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(playlistQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PlayList allPlayList = new PlayList();
                allPlayList.setPlaylistId(rs.getInt("playlist_Id"));
                allPlayList.setPlaylistName(rs.getString("playlist_name"));
                allPlayList.setUserId(rs.getInt("userId"));

                if (allPlayList.getUserId() == uId) {
                    playList.add(allPlayList);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return playList;
    }

    public void printPlaylistTable(List<PlayList> playList) {
        System.out.println("+---------------+---------------------+----------+");
        System.out.println("| Playlist ID   | Playlist Name       | User ID  |");
        System.out.println("+---------------+---------------------+----------+");
        for (PlayList playlist : playList) {
            System.out.format("| %-14d| %-20s| %-9d|\n", playlist.getPlaylistId(), playlist.getPlaylistName(), playlist.getUserId());
        }
        System.out.println("+---------------+---------------------+----------+");
    }

    public List<PlaylistSong> showSongsBySearchedPlayList(String songPlaylistQuery) {
        List<PlaylistSong>  playlistSongs = new ArrayList<>();

        try {
            PreparedStatement stmt = con.prepareStatement(songPlaylistQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PlaylistSong ListOfSongs = new PlaylistSong();
                ListOfSongs.setSongId(rs.getInt("s.id"));
                ListOfSongs.setSongName(rs.getString("s.song_name"));
                ListOfSongs.setSongDuration(rs.getString("song_duration"));
                playlistSongs.add(ListOfSongs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return playlistSongs;
    }

    public void printSongsBySearchedPlaylist(List<PlaylistSong> playListSong) {
        System.out.println("-------------------+------------------------+");
        System.out.println("ID   | Song Name                  | Duration |");
        System.out.println("-------------------+------------------------+");
        for (PlaylistSong song : playListSong) {
            System.out.println(song.getSongId() + "    |" + song.getSongName() + "        |" + song.getSongDuration());
        }
        System.out.println("-------------------+------------------------+");
    }

    public List<Artist> searchArtist() {
        List<Artist> artistList = new ArrayList<>();
        con = getConnection();
        System.out.println("Please type something to search artist. It will search if the entered value lies in the artist name:");
        String search = sc.next();
        try {
            String allArtistsQuery = "SELECT * FROM artist WHERE artist_name like '%" + search + "%';";
            PreparedStatement stmt = con.prepareStatement(allArtistsQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Artist artist = new Artist();
                artist.setArtistId(rs.getInt("artist_id"));
                artist.setArtistName(rs.getString("artist_name"));
                artistList.add(artist);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return artistList;
    }

    public void printArtistTable(List<Artist> artistList) {
        System.out.println("+---------------+---------------------------------+");
        System.out.println("| Artist ID     | Artist Name     |");
        System.out.println("+---------------+---------------------------------+");

        for (Artist artist : artistList) {
            System.out.format("| %-21d | %-23s |\n", artist.getArtistId(), artist.getArtistName());
        }
        System.out.println("+---------------+---------------------------------+");
    }

    // songs present in playlist
    public List<PlaylistSong> showSongsByPlayList(String songPlaylistQuery) {
        List<PlaylistSong> playListSongs = new ArrayList<>();
            try {
                PreparedStatement stmt = con.prepareStatement(songPlaylistQuery);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    PlaylistSong allPlayList = new PlaylistSong();
                    allPlayList.setSongId(rs.getInt("id"));
                    allPlayList.setSongName(rs.getString("song_name"));
                    allPlayList.setPathOfSong(rs.getString("path_name"));
                    playListSongs.add(allPlayList);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return playListSongs;
        }

    public void printSongsInPlaylist(List<PlaylistSong> playListSongs) {
        System.out.println("+--------+-----------+-----------+");
        System.out.println("| Song Id  | Song Name            |");
        System.out.println("+--------+-----------+-----------+");
        for (PlaylistSong playListSong : playListSongs) {
            System.out.printf("| %-8d| %-22s|\n", playListSong.getSongId(), playListSong.getSongName());
        }
        System.out.println("+--------+-----------+----------+");
    }

    public void printGenreListSongs(List<Song> genreListSong) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Song ID  | Song Name                      | Song Duration |");
        System.out.println("-------------------------------------------------------------");
        for (Song genreListSongs : genreListSong) {
            System.out.printf("| %-8d | %-30s | %-14s |\n", genreListSongs.getId(), genreListSongs.getSongName(), genreListSongs.getSongDuration());
        }
        System.out.println("-------------------------------------------------------------");
    }
    public List<Song> showSongs(String art) {
        con = getConnection();
        List<Song> artistListSong = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(art);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Song artistListSongs = new Song();
                artistListSongs.setId(rs.getInt("id"));
                artistListSongs.setSongName(rs.getString("song_name"));
                artistListSongs.setSongDuration(rs.getString("song_duration"));
                artistListSong.add(artistListSongs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return artistListSong;
    }

    public void printSongsByArtist(List<Song> artistListSong) {
        System.out.println("-------------------+------------------------+");
        System.out.println("ID   | Song Name                  | Duration |");
        System.out.println("-------------------+------------------------+");
        for (Song song : artistListSong) {
            System.out.println(song.getId() + "    |" + song.getSongName() + "        |" + song.getSongDuration());
        }
        System.out.println("-------------------+------------------------+");
    }

    public List<Album> searchAlbum() {
        List<Album> albumListList = new ArrayList<>();
        con = getConnection();
        System.out.println("Please type something to search Album. It will search if the entered value lies in the album name:");
        String search = sc.next();
        try {
            String allAlbumQuery = "SELECT * FROM album WHERE album_name like '%" + search + "%';";
            PreparedStatement stmt = con.prepareStatement(allAlbumQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Album album = new Album();
                album.setAlbumId(rs.getInt("album_id"));
                album.setAlbumName(rs.getString("album_name"));
                albumListList.add(album);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return albumListList;
    }

    public void printAlbumList(List<Album> albumList) {
        System.out.println("--------------------------------------------------");
        System.out.println("| Album ID | Album Name                           |");
        System.out.println("---------------------------------------------------");
        for (Album album : albumList) {
            System.out.printf("| %-8d | %-35s |\n", album.getAlbumId(), album.getAlbumName());
            System.out.println("------------------------------------------------");
        }
    }

    public void printAlbumListSongs(List<Song> albumListSong) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("| Song ID  | Song Name                      | Song Duration |");
        System.out.println("-------------------------------------------------------------");
        for (Song albumListSongs : albumListSong) {
            System.out.printf("| %-8d | %-30s | %-13s |\n", albumListSongs.getId(), albumListSongs.getSongName(), albumListSongs.getSongDuration());
        }
        System.out.println("-------------------------------------------------------------");
    }

    public List<Genre> searchGenre() {
        List<Genre> genreListList = new ArrayList<>();
        con = getConnection();
        System.out.println("Please type something to search Genre. It will search if the entered value lies in the genre name:");
        String search = sc.next();
        try {
            String allAlbumQuery = "SELECT * FROM genre WHERE genre_name like '%" + search + "%';";
            PreparedStatement stmt = con.prepareStatement(allAlbumQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setGenreName(rs.getString("genre_name"));
                genreListList.add(genre);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return genreListList;
    }
    public void printGenreList(List<Genre> genreList) {
        System.out.println("--------------------------------------------------");
        System.out.println("| Genre ID | Genre Name                          |");
        System.out.println("--------------------------------------------------");
        for (Genre genre : genreList) {
            System.out.printf("| %-8d | %-35s |\n", genre.getGenreId(), genre.getGenreName());
            System.out.println("----------------------------------------------");
        }
    }
}


