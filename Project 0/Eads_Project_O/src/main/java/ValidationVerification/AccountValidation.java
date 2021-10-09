package ValidationVerification;

import MenuPages.CreateWithDep;
import models.User;

import java.io.IOException;
import java.sql.SQLException;

public class AccountValidation {
    /*
     * accountCheck checks a string to ensure that it is of the proper length and
     * contains numbers.
     */
    public static int accountCheck(String input, String choice, User user) throws SQLException, IOException {
    int accNum = 0;
    if (!input.matches("[0-9]*")) {
        System.out.println("I'm sorry the account number must consist of numbers.");
        CreateWithDep.createWithDep(choice, user);
    } else if (input.length() != 5) {
        System.out.println("The account number is only five digits long.");
        CreateWithDep.createWithDep(choice, user);
    } else {
        accNum = Integer.parseInt(input);
    }
    return accNum;
}
}
