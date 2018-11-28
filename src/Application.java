import java.sql.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;


public class Application {
    public static void main (String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/Dorms?serverTimezone=UTC&useSSL=TRUE";
        try {
            DriverManager.registerDriver(new Driver());
            Connection conn = DriverManager.getConnection(dbURL, "student", "password");
            //dispMaintDepRep(conn);
            //loadData(conn);
            runProgram(conn);
            //checkAvailability(conn);
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

    public static void printMainMenu(){
        menuDisplayTitleHeader();
        System.out.println("1. Resident Login");
        System.out.println("2. Applicant Registration/Apply");
        System.out.println("3. Admin");
        System.out.println("4. Quit");
    }

    public static Boolean runProgram(Connection conn){
        Scanner userInput = new Scanner(System.in);
        printMainMenu();
        String userResponse = userInput.next();
        if(userResponse.equals("3")){
            adminScreen(conn);
            return true;
        }
        if(userResponse.equals("2")){
            applicantView(conn);
            return true;
        }
        if(userResponse.equals("1")){
            login(conn);
            return true;
        }
        if (userResponse.equals("4")){
            return false;
        }
        else{
            return true;
        }
    }


    public static void menuDisplayTitleHeader(){
        printStars(80);
        System.out.println();
        printTabs(4);
        printStars(12);
        System.out.println();
        printTabs(3);
        System.out.print("Welecome to the Housing System\n");
        printTabs(4);
        printStars(12);
        System.out.println();
        printStars(80);
        System.out.println();
        //prints header file
        //header file includes option chosen
    }

    public static void menuDisplaySubtitleHeader(String selection){
        printStars(80);
        System.out.println();
        printTabs(3);
        System.out.print("Welecome to bellevue College Housing System");
        System.out.println();
        printTabs(4);
        System.out.println(selection+"\n\n");
        printStars(80);
        System.out.println();
    }

    public static void printStars(int numStars){

        for(int i =0; i < numStars; i++){
            System.out.print("*");
        }
    }

    public static void printTabs(int numTabs){

        for(int i =0; i < numTabs; i++){
            System.out.print("\t");
        }
    }


    public static void adminScreen(Connection conn){
        int userID = login(conn);
        if(adminLogin(conn, userID)) {
            boolean running = true;
            menuDisplaySubtitleHeader("Administrators Staff");
            while(running == true) {
                System.out.println();
                int choice = printOptionsAdministrators();
                if (choice == 4) {
                    dispMaintDepRep(conn);
                } else if (choice == 6){
                 running = false;
                }
                else {
                    System.out.println("Please excuse our dust! That feature is still under construction.");
                }
            }
        } else {
            System.out.println("Sorry, that user is not an administrator");
        }
    }
    public static int printOptionsAdministrators(){
            System.out.println("1. Manage Residents");
            System.out.println("2. Manage Applicants");
            System.out.println("3. Demographic Studies");
            System.out.println("4. Manage Maintenance Orders");
            System.out.println("5. Administrative Reports");
            System.out.println("6. Quit");
            return getIntInput("Please select an option ");
    }


    public static String getUserInput(){
        Scanner userInput = new Scanner(System.in);
        String userResponse = userInput.next();
        // userInput.close();
        return userResponse;
    }

    public static String getStringInput(String question){
        System.out.print(question);
        String userInput = getUserInput();
        System.out.println();
        return userInput;
    }
    public static int getIntInput(String question){
        Boolean success = false;
        int result = 0;
        while(success == false) {
            try {
                System.out.print(question);
                success = true;
                result = Integer.parseInt(getUserInput());
            } catch (NumberFormatException ex) {
                System.out.println("Improper format. Please enter a number.");
                success = false;
            }
        }
        System.out.println();
        return result;
    }

    public static void applicantView(Connection conn){
        menuDisplaySubtitleHeader("Application Menu");
        System.out.println("Please log in.");
        login(conn);
        boolean running = true;
        while (running ){
            System.out.println("Please choose an option.\n1. View available housing\n2. Submit an application\n3. Go back\n");
            int userChoice = getIntInput("Please choose an option.\n1. View available housing\n2. Submit an application\n3. Go back\n");
            if (userChoice == 1){
                checkAvailability(conn);
            } else if (userChoice == 2){
                applicationForm(conn);
            } else if (userChoice == 3){
                running = false;
            } else {
                System.out.println("Sorry, that is not a known option.");
            }
        }
    }

    public static void applicationForm(Connection conn){
        int appResult = 0;
        menuDisplaySubtitleHeader("Application Form");
        System.out.println("Fill out this Form Below: ");
        int idNum = getIntInput("Student ID Number: ");
        String rmtPref = getStringInput("Roommate preference? (Enter NA for no preference) ");
        String spouse = getStringInput("Will a spouse be living with you? If yes, enter name. If no, enter NA. ");
        System.out.println("Select 3 room preferences ");
        int rPref1 = getIntInput("    First room priority: ");
        int rPref2 = getIntInput("    Second room priority: ");
        int rPref3 = getIntInput("    Third room priority: ");
        String query = "INSERT INTO Application VALUES (NULL,"+idNum+",0,'"+getDate()+"','"+rmtPref+"','"+spouse+"',"+rPref1+","+rPref2+","+rPref3+");";
        insertApp(conn, query);

    //Here, we will take the application we just made and try to fulfill it
//        query = "SELECT AppNum FROM Application";
//        try {
//            ResultSet r = getResultSet(conn, query);
//            r.last();
//            int appNum = r.getInt(1);
//            appResult = fulfillApplication(conn, appNum);
//        }catch (SQLException e){
//            System.out.println(e);
//        }
//
//
//
//        if (appResult == 1){
//            System.out.println("Congratulations! You are now the proud resident of ");
//        }
    }
    public static ResultSet getResultSet(Connection conn, String query){
        ResultSet r = null;
        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.clearParameters();
            r = p.executeQuery();
        }catch (SQLException e){
            System.out.println(e);
        }
        return  r;
    }
    public static  void  insertApp(Connection conn, String query){
        try{
            PreparedStatement p = conn.prepareStatement(query);
            p.clearParameters();
            p.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Thank you for your application.");
    }


    //Returns the idNum of the person logging in
    public static int login(Connection conn){
        boolean success = false;
        int idNum = 0;
        while (success == false){
            String username = getStringInput("Username: ");
            String password = getStringInput("Password: ");
            try{
                success = true;
                String query = "SELECT COUNT(FName), FName, LName, IDnum FROM Person WHERE UserName= '"+username+"' AND Passwrd = '"+password+"';";
                PreparedStatement p = conn.prepareStatement(query);
                p.clearParameters();
                ResultSet r = p.executeQuery();
                r.next();
                if(r.getInt(1) == 0){
                    System.out.println("Incorrect username or password.");
                    success = false;
                } else {
                        String fName = r.getString(2);
                        String lName = r.getString(3);
                        idNum = r.getInt(4);
                        System.out.println("Welcome to the BC Housing Hub " + fName + " " + lName + ".");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return idNum;
    }

    public static boolean adminLogin(Connection conn, int userId){
        try{
            String query = "SELECT COUNT(IDnum) FROM Administrator WHERE IDnum = "+userId+";";
            PreparedStatement p = conn.prepareStatement(query);
            p.clearParameters();
            ResultSet r = p.executeQuery();
            r.next();
            if(r.getInt(1) == 0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }


    static void loadData(Connection conn){
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
            //Pulls data from the result to build the return
                String rType = r.getString(1); String rNum = r.getString(2);
                String bNum = r.getString(3); String village = r.getString(4);
                String address = r.getString(5);
            //Builds the block of text to tell the rooms available
                System.out.println("ROOM TYPE: " + rType + "\nVILLAGE: " + village + "\nADDRESS: " + address);
                System.out.println("--------------------------------------------------------");
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    static void dispMaintDepRep(Connection conn) {
        try {
            String date = getStringInput("What date would you like to look for? (YYYY-MM-DD format): ");
            String query = "SELECT RoomNum, BuildingNum FROM MaintenanceRequest WHERE SubmissionDate = '"+date+"';";
            System.out.println(query);
            PreparedStatement p = conn.prepareStatement(query);
            p.clearParameters();
            ResultSet r = p.executeQuery();
            printStars(90); System.out.println();
            System.out.println("The following residences submitted maintenance requests on "+date);
            System.out.println("Building Number     Room Number\n");
            while(r.next()) {
                String rmNum = r.getString(1);
                String bldNum = r.getString(2);
                System.out.println(bldNum+"                 "+rmNum);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

