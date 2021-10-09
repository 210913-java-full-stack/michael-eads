package MenuPages;

import DAOs.BankingDao;
import models.User;
import utils.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import static ValidationVerification.PassVerification.passVer;
import static utils.Reformat.Manipulation;

public class Login {
    /*
     * Input gets the users name and password, sends the password to get verified,
     * and creates a "User"
     */
    public static void  login() throws SQLException, IOException {
        String first; String last; String input; boolean passVer = false;
        System.out.println("<><><><><><>LOGIN<><><><><><>" +
                "\n   Hello, valued customer.   \nPlease enter your first name.");
        Scanner incoming = new Scanner(System.in);
        input = incoming.next();
        first = Manipulation(input);
        User user = new User(); user.setFirst_name(first);
        System.out.print("Your last name?  ");
        input = incoming.next();
        last = Manipulation(input);
        user.setLast_name(last);
        do {
            System.out.print("Password?  ");
            input = incoming.next();
            passVer(user, input);
            input.toLowerCase();
            if(input.equals("q")){
                Login.login();
            }
        }
        while (passVer(user, input) == false);
        user.setPassword(input);
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            user.setSSN(dao.getSSN(user));
        }
        catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        MemberMenu.member(user);
    }
}
