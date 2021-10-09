import DAOs.BankingDao;
import Exceptions.PersonExistsException;
import Exceptions.SsnErrorException;
import MenuPages.Login;
import MenuPages.MainMenu;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Engine {
    /*
     * The start of all the fun. Also, where I debugged a lot of code pieces.
     * I should have every menu return here before going to the next.
     */
    public static void main(String args[]) throws SsnErrorException, SQLException, IOException {
        MainMenu.mainMenu();
//////////////////////////////////////DEBUG\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//        try {
//            Connection conn = ConnectionManager.getConnection();
//            BankingDao dao = new BankingDao(conn);
//            if (dao.custName(1414)) {
//                System.out.println("I'm sorry, it seems you already have an account." +
//                        "\nPlease log in.");
//                Login.login();
//            }
//        }
//        catch (SQLException | IOException | PersonExistsException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("Thank you, now enter a password you would like associated with your account.\n" +
//                "Passwords need to be over eight (8) characters long, and contain at least:\none (1) capital " +
//                "letter, one (1) lower case letter, and one (1) number.");

    }
}