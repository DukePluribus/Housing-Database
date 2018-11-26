import java.sql.*;
import java.io.*;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;


public class Application {
    public static void main (String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/Dorms?serverTimezone=UTC&useSSL=TRUE";
        try {
            DriverManager.registerDriver(new Driver());
            Connection conn = DriverManager.getConnection(url, "student", "password");

        } catch (SQLException ex){
            System.out.println(ex);
        }
        loadData("test.txt"); //Need to write file loader method





    }


    /*
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    All the helper methods are below here
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    static void loadData(String inputFile){
        System.out.println("File " + inputFile +  " is loaded");
    }
}

