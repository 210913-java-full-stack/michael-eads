package MenuPages;

import Exceptions.PersonDontExistsException;
import Exceptions.SsnErrorException;
import models.User;
import utils.DisplayBalance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static utils.Reformat.Manipulation;

public class MemberMenu {

    /*
     * Member menu will take the name and password from either create user menu or the
     * log in menu. Display the menu, and then we will then go to the
     * Create/Withdraw/Deposit menu.
     */

    public static void member(User user) throws SQLException, IOException, SsnErrorException, PersonDontExistsException {
        int ss = 0;
        String input;

        Scanner incoming = new Scanner(System.in);
        System.out.println("\n\nWhat would you like to do today?\nB)  View Accounts\n" +
                "D)  Make a Deposit\nW)  Make a Withdraw\nC)  Create a New Account\nL)  " +
                "Link an Account with Existing Member\nT)  Transfer Funds\nQ)  Quit");
        input = incoming.next();
        input = Manipulation(input);
        input = input.substring(0, 1);
        if (input.equals("D") || input.equals("W") || input.equals("C")) {
            CreateWithDep.createWithDep(input, user);
        } else if (input.equals("B")) {
            DisplayBalance.balance(user);
            MemberMenu.member(user);
        } else if(input.equals("L")){
            LinkAccount.shareAccount(user);
        }else if(input.equals("T")){
            TransferFunds.transferMenu(user);
        }
        else if (input.equals("Q")) {
            MainMenu.mainMenu();
        } else {
            System.out.println("Please enter the letter \"D\", \"W\", \"C\" or \"Q\"");
            MemberMenu.member(user);
        }

    }
}
