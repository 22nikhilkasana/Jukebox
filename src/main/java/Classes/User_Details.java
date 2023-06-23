package Classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User_Details
{
   private int userId;
   private String name;
   private String UserName;
   private String password;

    public User_Details() {}

    public User_Details(int userId, String name, String userName, String password) {
        this.userId = userId;
        this.name = name;
        UserName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Classes.User_Details{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", UserName='" + UserName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
