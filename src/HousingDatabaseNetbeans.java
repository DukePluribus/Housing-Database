

import java.util.Scanner;

/**
 *
 * @author jsaunders
 */
public class HousingDatabaseNetbeans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Menu display
        // multiple options
            //Resident Login
            //Registration/Application
            //Administration
            //Quit
            runProgram();   
    
        
        //menuDisplaySubtitleHeader("Administrators Staff");
         
        
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
    
    public static void printMainMenu(){
        menuDisplayTitleHeader();
        System.out.println("1. Resident Login");
        System.out.println("2. Applicant Registration/Apply");
        System.out.println("3. Admin");
        System.out.println("4. Quit");
    }
    
    public static void printOptionsAdministrators(){
     menuDisplaySubtitleHeader("Administrators Staff");
     System.out.println("1. Manage Residents");
     System.out.println("2. Manage Applicants");
     System.out.println("3. Demographic Studies");
     System.out.println("4. Manage Maintenance Orders");
     System.out.println("5. Administrative Reports");
     System.out.println("6. Quit");
    
    }
    
    public static void printOptionsApplicants(){
        System.out.println("1. Check Availability");
        //will check availability per category chosen
        System.out.println("2. Apply");
        //will give form for applicant to apply
    }
    
    public static String getUserInput(){
        Scanner userInput = new Scanner(System.in);
        String userResponse = userInput.next();
        return userResponse;    
    }
    
    public static void applicationForm(){
        menuDisplaySubtitleHeader("Application Form");
        System.out.println("Fill out this Form Below:");
        System.out.println("First Name:\nLastName:\nAddress:\nDOB:\n.\n.\n.");
    }
    
    public static void residentLogin(){
        menuDisplaySubtitleHeader("Resident Login");
        System.out.println("Username:");
        System.out.println("Password:");
    }
    
    public static Boolean runProgram(){
        Scanner userInput = new Scanner(System.in);
        printMainMenu();
        String userResponse = userInput.next();
        if(userResponse.equals("3")){
            printOptionsAdministrators();
            return true;
        }
        if(userResponse.equals("2")){
            applicationForm();
            return true;
        }
        if(userResponse.equals("1")){
            residentLogin();
            return true;
        }
        if (userResponse.equals("4")){
            return false;
        }
        else{
            return true;
        }
    
        
    }
    
    
    
    
    
}
    
    
    
    
    
    

