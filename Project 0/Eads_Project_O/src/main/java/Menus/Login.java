package Menus;

import DAOs.BankingDao;
import Exceptions.passwordFailedException;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static utils.Reformat.Manipulation;

public class Login {
    public static void login() throws SQLException, IOException {
        String first; String last; String input;
        System.out.println("Hello, valued customer.\nPlease enter your first name.");
        Scanner incoming = new Scanner(System.in);
        input = incoming.next();
        first = Manipulation(input);                                        //edit what they typed
        System.out.println("Your last name?");                              //request last name
        input = incoming.next();                                            //read whet they typed
        last = Manipulation(input);

        System.out.println("Password?");
        input = incoming.next();
        //authenticate username and password
        try{
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            if(!dao.passVer(first, last, input)){
               System.out.println("Password failed, please try again.");
            }
        }
        catch (SQLException | IOException | passwordFailedException e)
        {
            System.out.println(e.getMessage());
            login();
        }
        MemberMenu.member(first,last,input);
        //DEBUG //System.out.println(first +" "+last+" " + input);
    }
}
