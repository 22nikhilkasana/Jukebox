import Classes.User_Details;

import java.sql.*;
import java.util.Scanner;


public class LogInAndSignUp extends Connection_DB
{
    Connection con;
    User_Details details;
    Scanner sc = new Scanner(System.in);
    String name = "";

     public void registerNewUser()
    {
        details = new User_Details();
        con = getConnection();

        System.out.println("please enter user Id : ");
        int id = sc.nextInt();
        details.setUserId(id);
        System.out.println("please enter name of the user : ");
        String nameOfUser = sc.next();
        details.setName(nameOfUser);
        System.out.println("please enter username of the user : ");
        String userName = sc.next();
        details.setUserName(userName);
        System.out.println("please enter a password : ");
        String pass = sc.next();
        details.setPassword(pass);
        try
        {
            Statement st = con.createStatement();
            int result = st.executeUpdate("insert into users values("+ details.getUserId() + ", '" + details.getName() +"', '" + details.getUserName() +"', '" + details.getPassword() + "');");
            System.out.println(result+" Registration is completed. Please use username and password for login purpose.");
        }catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
    }
    public int loginUser(String userName, String pass) {
        con = getConnection();
        int userId = 0;
        try
        {
            String loginDet = "select * from users";
            PreparedStatement Stmt = con.prepareStatement(loginDet);
            ResultSet rs = Stmt.executeQuery();

            String email = "";
            String pword  = "";

            while (rs.next()) {
                email = rs.getString("User_Name");
                pword = rs.getString("Password");

                if(userName.equals(email) && pass.equals(pword))
                {
                    userId = rs.getInt("userId");
                    name = rs.getString("Name");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("problem is "+ e);
        }
        return userId;
    }

    public void userAction()
    {
        System.out.println("Main Module");
        System.out.println("----*------");
        System.out.println("Which action you want to perform please select accordingly");
        System.out.println("1 --> Create playlist");
        System.out.println("2 --> Insert song into playlist");
        System.out.println("3 --> Show playlist");
        System.out.println("4 --> Listen all songs ");
        System.out.println("5 --> Search Artist, Playlist, Genre, Album");
        System.out.println("6 --> To exit main module");
        System.out.println("---------------*----------------");
    }

    public void welcome()
    {
        System.out.println("                                                                ------*------");
        System.out.println("                                                               |!! JUKEBOX !!|");
        System.out.println("                                                                ------*------");
        System.out.println("                                                   -----------                 -----------");
        System.out.println("                                                  | !! WELCOME !!                         |");
        System.out.println("                                                  |            !! TO !!                   |");
        System.out.println("                                                  |                  !! MUSIC !!          |");
        System.out.println("                                                  |                           !! WORLD !! |");
        System.out.println("                                                   ---------------------------------------");

    }
}
