
import Classes.*;

import java.util.List;
import java.util.Scanner;

public class JukeBox_Impl
{
    public static void main(String[] args)
    {
        String songDetails;
        int choice;
        Scanner sc = new Scanner(System.in);
        LogInAndSignUp login = new LogInAndSignUp();   //object for login and registration page
        DataInsertionInTable data = new DataInsertionInTable(); //object for data insertion table
        FetchDataFromDataBase fetch = new FetchDataFromDataBase(); //object of class for fetching data from database

      do {
          System.out.println("Please enter 1 for Login and 2 for new registration");

          choice = sc.nextInt();
          switch (choice) {
              case 1: {
                  System.out.println("*------------please enter details of the user for which you want to login-----------*");

                  System.out.println("please enter username : ");
                  String userName = sc.next();
                  System.out.println("please enter password : ");
                  String pass = sc.next();
                  if (login.loginUser(userName, pass) >0) {
                      System.out.println( login.name.toUpperCase() +" : You have logged in successfully!! ");
                      System.out.println("*-----------------+-----------------*---------------+-------------*");

                      login.welcome();
                  do {
                          login.userAction(); // calling method for the actions that user can take
                          choice = sc.nextInt();
                          switch (choice) {
                              case 1: {   // to create new playlist for a user
                                  data.createPlaylist();
                                  break;
                              }
                              case 2:{  // to enter song into playlist

                                  // to print list of playlist so that user can select in which playlist user want to add song
                                  System.out.println("Playlist Table is to check that which playlists are exist for user");
                                  int uId = login.loginUser(userName, pass);
                                  String playlistQuery = "SELECT * FROM playlist where userId ="+ uId;
                                  List<PlayList> playList = fetch.PlayList(playlistQuery, uId);
                                  fetch.printPlaylistTable(playList);

                                  // to get the list of the song so that through id user can add the song into selected playlist
                                  System.out.println("Songs list is for user to select id of song to add in playlist");
                                  List<Song> songList = fetch.showSongList();
                                  fetch.printSongTable(songList);

                                  //  to insert songs into playlist
                                  data.insertSongIntoPlaylist();
                                  break;
                              }

                              case 3: {   // to show the whole playlist
                                  int uId = login.loginUser(userName, pass);
                                  String playlistQuery = "SELECT * FROM playlist where userId ="+ uId;
                                  List<PlayList> playList = fetch.PlayList(playlistQuery, uId);
                                  if(!playList.isEmpty())
                                  {
                                      fetch.printPlaylistTable(playList);
                                      // to show songs present in the playlist
                                      System.out.println("please enter playlist id of the playlist to fetch and play songs ");
                                      int playlistId = sc.nextInt();
                                      String songPlaylistQuery = "select s.id,song_name,s.song_duration, s.path_name from song s, playlist p, playlist_song ps where ps.playlist_Id=" + playlistId + " and p.playlist_Id = ps.playlist_Id and s.id = ps.id;\n";
                                      List<PlaylistSong> list = fetch.showSongsByPlayList(songPlaylistQuery);
                                      fetch.printSongsInPlaylist(list);
                                      fetch.playSong(songPlaylistQuery);
                                  }
                                  else System.out.println("Entered playlist Id does not exist for this user");
                                  break;
                              }
                              case 4: {
                                  // play all songs
                                  List<Song> songList = fetch.showSongList();
                                  fetch.printSongTable(songList);

                                  // below query will provide path of the songs present in list
                                  songDetails = "select id, song_name, song_duration, path_name from song";
                                  fetch.playSong(songDetails);
                                  break;
                              }
                              case 5:  // searching portion
                              {
                                  do {
                                      System.out.println("Search Module");
                                      System.out.println("------*------");
                                      System.out.println("Please enter number to search according to requirement");
                                      System.out.println("1 ---- Artist");
                                      System.out.println("2 ---- PlayList");
                                      System.out.println("3 ---- Genre");
                                      System.out.println("4 ---- Album");
                                      System.out.println("5 ---- Exit");
                                      choice = sc.nextInt();
                                      switch (choice) {
                                          case 1: {
                                              // method to print list of artist after searching
                                              List<Artist> artistList = fetch.searchArtist();
                                              if(!artistList.isEmpty())
                                              {
                                                  fetch.printArtistTable(artistList);
                                                  // method used to show songs as per artist id
                                                  System.out.println("please enter artist id of the artist to retrieve and play songs ");
                                                  int artistId = sc.nextInt();
                                                  String songPlaylistQuery = "select id,song_name, song_duration,path_name from song where artist_id= "+artistId+";\n";
                                                  List<Song> artistListSong = fetch.showSongs(songPlaylistQuery);
                                                  fetch.printSongsByArtist(artistListSong);
                                                  // method used to play songs
                                                  fetch.playSong(songPlaylistQuery);
                                              }
                                              else System.out.println("Searched artist does not exist");
                                              break;
                                          }
                                          case 2: {   // search playlist
                                              int uId = login.loginUser(userName, pass);
                                              System.out.println("please type something to search playlist it will search if entered value lie in playlist name");
                                              String search = sc.next();
                                              String allSongsQuery = "SELECT * FROM playlist where playlist_name like '%"+ search +"%';";
                                              List<PlayList> playList = fetch.PlayList(allSongsQuery, uId);

                                              if(!playList.isEmpty())
                                              {
                                                  fetch.printPlaylistTable(playList);
                                                  System.out.println("please enter playlist id to retrieve and play songs ");
                                                  int playListId = sc.nextInt();
                                                  String songPlaylistQuery = "select s.id,song_name,s.song_duration,s.path_name from song s, playlist p, playlist_song ps where ps.playlist_Id=" + playListId + " and p.playlist_Id = ps.playlist_Id and s.id = ps.id;\n";
                                                  List<PlaylistSong> songList= fetch.showSongsBySearchedPlayList(songPlaylistQuery);
                                                  fetch.printSongsBySearchedPlaylist(songList);
                                                  fetch.playSong(songPlaylistQuery);
                                              }
                                              else {
                                                  System.out.println("Searched playlist does not exist");
                                              }
                                              break;
                                          }
                                          case 3: {   // searched list of genre
                                              List<Genre> genre = fetch.searchGenre();
                                              if(!genre.isEmpty())
                                              {
                                                  fetch.printGenreList(genre);
                                                  //method used to show songs as per genre id
                                                  System.out.println("please enter genre id of the genre to retrieve and play songs ");
                                                  int genreId = sc.nextInt();
                                                  String songPlaylistQuery = "select id,song_name,path_name, song_duration from song where genre_id= "+genreId+";\n";
                                                  List<Song> genreListSong= fetch.showSongs(songPlaylistQuery);
                                                  fetch.printGenreListSongs(genreListSong);
                                                  //to play-songs as per genre list
                                                  fetch.playSong(songPlaylistQuery);
                                              }
                                              else System.out.println("Searched genre does not exist");
                                              break;
                                          }
                                          case 4: {
                                              // searched list of  album
                                              List<Album> albumList = fetch.searchAlbum();

                                              if(!albumList.isEmpty())
                                              {
                                                  fetch.printAlbumList(albumList);
                                                  System.out.println("please enter album id of the album to retrieve and play songs ");
                                                  int albumId = sc.nextInt();
                                                  String songPlaylistQuery = "select id,song_name, song_duration , path_name from song where album_id= "+albumId+";\n";
                                                  List<Song> albumListSong = fetch.showSongs(songPlaylistQuery);
                                                  fetch.printAlbumListSongs(albumListSong);
                                                  fetch.playSong(songPlaylistQuery);
                                              }
                                              else System.out.println("Searched album does not exist");
                                              break;
                                          }
                                          case 5:
                                          {
                                              System.out.println("You are exited from searching module");
                                              break;
                                          }
                                          default:
                                              System.out.println("you have entered wrong number please select correct number ");
                                      }
                                      System.out.println("--------------*----------------*----------------*-----------------");
                                      System.out.println("To visit search module again then enter 1 otherwise enter 0");
                                      choice = sc.nextInt();
                                  } while (choice == 1);
                                  break;
                              }
                              case 6:
                              {
                                  System.out.println("---------------*----------------");
                                  System.out.println("You are exited from main module.");
                                  break;
                              }
                              default:
                                  System.out.println("You have entered wrong number please select correct number.");
                          }
                          System.out.println("------------------------*________________________*------------------------*____________________");
                          System.out.println("To visit main module again for the same user then please enter 1 to continue or enter 0 to exit");
                          choice = sc.nextInt();
                      }while (choice == 1);
                  }
                  else{
                      System.out.println("Check username and password again, Login Failed!!");
                      System.out.println("Please enter correct username and password");
                  }
                  break;
              }
              case 2: {
                  System.out.println("*------------please enter details of the user for new registration--------------*");
                  login.registerNewUser(); // calling method for new registration
                  break;
              }
              default: System.out.println("please select choice correctly");
          }
          System.out.println("If you want to visit music world again then please enter 1 to continue or enter 0 to exit");
          choice = sc.nextInt();
      }while(choice==1);
        System.out.println("Bye, Have a nice day !!");
    }
}

