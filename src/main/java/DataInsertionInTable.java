import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DataInsertionInTable extends Connection_DB
{
    Scanner sc = new Scanner(System.in);
    int choice;
    Connection con;

    // method to insert data in table
    public void insertInSongTable() {
        con = getConnection();
        do {
            System.out.println("please enter Classes.Song ID");
            int songId = sc.nextInt();
            System.out.println("please enter name of the song");
            String songName = sc.next();
            System.out.println("please enter id of the artist");
            int artistId = sc.nextInt();
            System.out.println("please enter id of the genre");
            int genreId = sc.nextInt();
            System.out.println("please enter id of the album");
            int albumId = sc.nextInt();
            System.out.println("please enter duration of the song");
            String songDuration = sc.next();
            String path="D:\\\\NIIT\\\\Nikhil_Projects\\\\JukeBox_Project\\\\Songs\\\\";
            String pathName = path + songName;

            try {
                Statement st = con.createStatement();
                int res = st.executeUpdate("insert into song values(" + songId + ",'" + songName + "'," + artistId + "," + genreId + "," + albumId +",'"+songDuration + "', '" + pathName +"');");
                System.out.println("Record Inserted in song table " + res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("if you want to add another song details then please enter 1 to continue or enter 0 to exit");
            choice = sc.nextInt();
        }while(choice ==1);
        System.out.println("your details have been saved in database");
    }

    // method to create a playlist
    public void createPlaylist()
    {
        con = getConnection();
        do {
            System.out.println("please enter playlist ID");
            int playListId = sc.nextInt();
            System.out.println("please enter name of the playlist");
            String playListName = sc.next();
            System.out.println("please enter user Id");
            int userId = sc.nextInt();

            try {
                Statement st = con.createStatement();
                int res = st.executeUpdate("insert into playlist values(" + playListId + ", '" + playListName + "', " + userId +");");
                System.out.println(res+"- Record Inserted in playlist table ");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("if you want to create another playlist then please enter 1 to continue or enter 0 to exit");
            choice = sc.nextInt();
        }while(choice ==1);
        System.out.println("your details have been saved in database");
    }



    public void insertSongIntoPlaylist()
    {
        con = getConnection();

        do {
            System.out.println("please enter playlist-song Id for transaction table");
            int psid = sc.nextInt();
            System.out.println("please enter playlist id in which you want to add song");
            int playlistId = sc.nextInt();
            System.out.println("please enter song id that you want to add");
            int songId = sc.nextInt();

            try {
                Statement st = con.createStatement();
                String insertSongQuery = "insert into playlist_song values("+ psid+","+ playlistId+ ","+songId+ ");";
                int res = st.executeUpdate(insertSongQuery);
                System.out.println(res+"- selected song has been added to selected playlist");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("if you want to add  another song in the playlist then please enter 1 to continue or enter 0 to exit");
            choice = sc.nextInt();
        }while(choice ==1);
    }
}
