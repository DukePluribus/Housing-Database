import java.sql.*;
import java.io.*;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;


public class Application {
    public static void main (String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/Dorms?serverTimezone=UTC&useSSL=TRUE";
        try {
            DriverManager.registerDriver(new Driver());
            Connection conn = DriverManager.getConnection(dbURL, "student", "password");
            loadData("test.txt", conn); //Need to write file loader method

        } catch (SQLException ex){
            System.out.println(ex);
        }

        

    }


    /*
    /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
    \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
    |||||    All the helper methods are below here          |||||
    /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
    \/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
     */
    static void loadData(String inputFile, Connection conn){
        File file = new File(inputFile);
        Scanner scan = null;
        try{
            scan = new Scanner(file);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String query = "INSERT INTO User VALUES (" + line + ");";
                System.out.println(query);
                PreparedStatement p = conn.prepareStatement(query);
                p.clearParameters();
                p.execute();
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        } catch(SQLException e){
            System.out.println(e);
        }
        System.out.println("File " + inputFile +  " is loaded");
    }
}

