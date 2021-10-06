package Menus;

import DAOs.BankingDao;
import Exceptions.PersonExistsException;
import Exceptions.SsnErrorException;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import static utils.Reformat.Manipulation;
import static utils.Reformat.ssnVerification;

public class CreateUser {

    public static void createUser() throws SsnErrorException {
        String first; String last; int ss = 0; String pw = "";

        Scanner incoming = new Scanner(System.in);                          //set up to listen
        boolean exists = false; boolean ssn = false;                         //set boolean start value
        System.out.println("Please enter your first name.");                //request first name
        String input = incoming.next();                                     //read what they typed
        first = Manipulation(input);                                        //edit what they typed
        System.out.println("Your last name?");                              //request last name
        input = incoming.next();                                            //read whet they typed
        last = Manipulation(input);                                         //edit what they typed

        //send first and last to authentication to see if they exists
        try{
            //validate the login information in the UserDao instance 'dao'
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            if(dao.custName(first,last)){
                System.out.println("I'm sorry, that name already exist. please check spelling and try again.");
            }
        }
        catch (SQLException | IOException | PersonExistsException e)
        {
            System.out.println(e.getMessage());
            createUser();
        }
        while(!ssn)
        {
            System.out.println("For our records we need the last four " +       //request last 4 of ssn
                    "digits of your social security number");
            input = incoming.next();                                            //read what they typed
            ss = ssnVerification(input);                                    //verify that the ssn is 4 numbers long
            if (ss==0)
            {                                                                   //if ss = 0, ssn contained letters
                System.out.println("Your SSN should only consist of " +
                        "numbers, letters are not permitted");
            }
            else if (ss==1)
            {
                System.out.println("We only need the last FOUR (4) of your social.");
            }
            else
            {
                ssn = true;
            }
        }
        System.out.println("Thank you, now enter a password you " +             //request password
                        "would like associated with your account.\nPasswords " +
                        "can be anything.");
        input = "1t3f5s7e9t11";
        while(input.length() > 10) {
                System.out.println("The password must be less than 10 characters.\n");
                input = incoming.next();
        }
        pw = input;
        try{
            //validate the login information in the UserDao instance 'dao'          //createUser runs all the way to
            Connection conn = ConnectionManager.getConnection();                    //here but just prints the test line
            BankingDao dao = new BankingDao(conn);
            if(dao.newCust(first, last, pw, ss)){
                MemberMenu.member(first, last, pw);
            }

            System.out.println("first: "+first +"\nlast: "+ last +"\npw is: " +pw+"\nss: "+ss);//dao
        }
        catch (SQLException | IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
