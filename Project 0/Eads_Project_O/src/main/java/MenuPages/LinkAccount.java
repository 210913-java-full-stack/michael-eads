package MenuPages;

import DAOs.BankingDao;
import Exceptions.PersonDontExistsException;
import Exceptions.SsnErrorException;
import ValidationVerification.AccountValidation;
import ValidationVerification.CheckDetails;
import ValidationVerification.SocialValidation;
import models.User;
import utils.ConnectionManager;
import utils.DisplayBalance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static utils.ConnectionManager.conn;
import static utils.Reformat.Manipulation;

public class LinkAccount {
    /*
     * This class asks for the name and social security number of the person
     * the user wants to add to their account. Al though the SSN isn't necessary
     * to validate the account, I felt it was prudent to ask for more than their
     * name. If the person is not currently a member then they will be asked to
     * have that person create one
     */
    public static void shareAccount(User user) throws SQLException, IOException, SsnErrorException, PersonDontExistsException {
        Scanner incoming = new Scanner(System.in);
        boolean ssn = false; boolean validated = false;

        System.out.println("\n- WARNING - WARNING - WARNING -WARNING -WARNING -\n " +
                "Linking your account with another person gives \n that person permission " +
                "to make changes to your \n     account. \nDo you wish to continue?  (Y/N) ");
        String input = incoming.next();
        input = Manipulation(input);
        input = input.substring(0,1);
        if(input.equals("N")){
            MemberMenu.member(user);
        }
        else{
            System.out.println("\nWhat is the first name of the person you'd like to add?" +
                    "\n First: ");
            input = incoming.next();
            String first = Manipulation(input);
            System.out.println("Now, their last name?\n Last: ");
            input = incoming.next();
            String last = Manipulation(input);
            System.out.println("Finally, what is the last four of their social security number?" +
                    "\n SSN: ");
            while (!ssn) {
                input = incoming.next();
                ssn = SocialValidation.SocialVal(input);
            }
            int social = Integer.parseInt(input);
            CheckDetails.checkDeets(user, first, last, social);
            System.out.println("Now, finally, what account do you wish to share with them?\nPress " +
                    "\"B\" to see a list of your accounts and balances.");
            do{
                input = incoming.next();
                input = input.toUpperCase();
                if(input.substring(0,1).equals("B")){
                    DisplayBalance.balance(user);
                }
                else{
                    validated = AccountValidation.accountCheck(input);
                }
            }
            while(!validated);
            int acc = Integer.parseInt(input);
            try {
                conn = ConnectionManager.getConnection();
                BankingDao dao = new BankingDao(conn);
                dao.linkThem(social, acc);
            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
            MemberMenu.member(user);
        }
    }
}

