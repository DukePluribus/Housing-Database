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
            //loadData("test.txt", conn); //Need to write file loader method
            checkAvailability(conn);
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
        String[] tables = {"Person", "Employee","StudentAlumni", "Administrator","Maintainence", "Applicant",
                "Resident","RoomType","Room","Application","MaintenanceRequest"};
        for (String table : tables) {
            System.out.println("Loading " + table);
            File file = new File(table + ".txt");
            Scanner scan = null;
            try {
                scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    String query = "INSERT INTO " + table + " VALUES (" + line + ");";
                    //System.out.println(query);
                    PreparedStatement p = conn.prepareStatement(query);
                    p.clearParameters();
                    p.execute();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (SQLException e) {
                System.out.println(e);
            }
            System.out.println("File " + table + " is loaded");
        }
    }

    static void checkAvailability(Connection conn){
        try{
            String query = "SELECT TypeName, RoomNum, BuildingNum, Village, Address FROM RoomType, Room WHERE RoomStatus=0;";
            PreparedStatement p = conn.prepareStatement(query);
            p.clearParameters();
            ResultSet r = p.executeQuery();
            System.out.println("Available rooms:");
            System.out.println("--------------------------------------------------------");
            while (r.next()) {
                String rType = r.getString(1);
                String rNum = r.getString(2);
                String bNum = r.getString(3);
                String village = r.getString(4);
                String address = r.getString(5);
                System.out.println("ROOM TYPE: " + rType + "\nVILLAGE: " + village + "\nADDRESS: " + address);
                System.out.println("--------------------------------------------------------");
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}

