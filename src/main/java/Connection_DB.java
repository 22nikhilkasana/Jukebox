import java.sql.Connection;
import java.sql.DriverManager;

public class Connection_DB
{
    public Connection getConnection(){
        Connection con = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
           // System.out.println("Driver Loaded");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","root");
           // System.out.println("Connection Created!!");
        }
        catch (Exception e)
        {
            System.out.println("problem is "+e);
        }
        return con;
    }
}
