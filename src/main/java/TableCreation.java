import java.sql.Connection;
import java.sql.Statement;

public class TableCreation extends Connection_DB
{
    Connection con;

    // this method is used to create user table
    public void createUserTable(){
        con = getConnection();
        try
        {
            Statement st = con.createStatement();
            boolean result = st.execute("create table users(userId int(5) primary key, Name varchar(100), User_Name varchar(100), Password varchar(100))");
            if (!result)
                System.out.println("Users Table created!!");
        }catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
    }

    // this method is used to create artist table

    public void createArtistTable(){
        con = getConnection();
        try
        {
            Statement st = con.createStatement();
            boolean result = st.execute("create table artist(artist_id int(5) primary key, artist_name varchar(100))");
            if (!result)
                System.out.println("Classes.Artist Table created!!");
        }catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
    }

    // this method is used to create Classes.Album table

    public void createAlbumTable(){
        con = getConnection();
        try
        {
            Statement st = con.createStatement();
            boolean result = st.execute("create table album(album_id int(5) primary key, album_name varchar(100))");
            if (!result)
                System.out.println("Classes.Album Table created!!");
        }catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
    }

    // this method is used to create Classes.Genre table

    public void createGenreTable(){
        con = getConnection();
        try
        {
            Statement st = con.createStatement();
            boolean result = st.execute("create table genre(genre_id int(5) primary key, genre_name varchar(100))");
            if (!result)
                System.out.println("Classes.Genre Table created!!");
        }catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
    }
}
