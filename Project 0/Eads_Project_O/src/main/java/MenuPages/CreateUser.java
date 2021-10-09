package MenuPages;

import DAOs.BankingDao;
import Exceptions.PersonExistsException;
import models.User;
import utils.ConnectionManager;
import ValidationVerification.PasswordValidation;
import ValidationVerification.SocialValidation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import static utils.Reformat.Manipulation;

public class CreateUser {
    /*
     * This class has the user input their name and social security number,
     * checks the SSN to see if they already exist, and pending that, has them
     * give a password.
     * All that is then sent to the DAOs where they are used to create a new user.
     * They are also given a free checking account.
     */

    public static void createUser(){
        String first = "";
        String last = "";
        String input ="";
        boolean isPass = false;
        User user = new User();

        Scanner incoming = new Scanner(System.in);
        boolean ssn = false;
        while (first.length()==0) {
            System.out.println("Please enter your first name.");
            input = incoming.next();
            first = Manipulation(input);
        }
        user.setFirst_name(first);
        while (last.length()==0) {
            System.out.println("Your last name?");
            input = incoming.next();
            last = Manipulation(input);
        }
        user.setLast_name(last);
        System.out.println("For our records we need the last four " +
                "digits of your social security number");
        while (!ssn) {
            input = incoming.next();
            ssn = SocialValidation.SocialVal(input);
        }
        user.setSSN(Integer.parseInt(input));
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            if (dao.custName(user.getSSN())) {
                System.out.println("I'm sorry, it seems you already have an account." +
                        "\nPlease log in.");
                Login.login();
            }
            else{
                System.out.println("Thank you, now enter a password you would like associated with your account.\n" +
                        "Passwords need to be over eight (8) characters long, and contain at least:\none (1) capital " +
                        "letter, one (1) lower case letter, and one (1) number.");
                while (!isPass) {
                    System.out.print("\nPassword? ");
                    input = incoming.next();

                    if (PasswordValidation.PassVal(input)) {
                        user.setPassword(input);
                        isPass = true;

                        if (dao.newCust(user)) {
                            MemberMenu.member(user);
                        }
                    }
                }
            }
        }
        catch (SQLException | IOException | PersonExistsException e) {
            System.out.println(e.getMessage());
            createUser();
        }
    }
}
