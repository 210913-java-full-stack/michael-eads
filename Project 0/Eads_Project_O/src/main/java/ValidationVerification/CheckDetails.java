package ValidationVerification;

import DAOs.BankingDao;
import Exceptions.PersonDontExistsException;
import Exceptions.SsnErrorException;
import MenuPages.MemberMenu;
import models.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CheckDetails {
    /*
     * This class takes in a first, last name, and ssn, then checks them
     * against the database to see if they exist. If the object returns,
     * then the user exist.
     */
    public static void checkDeets (User user, String first, String last, int ssn) throws SQLException, IOException, SsnErrorException, PersonDontExistsException {
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            User user2 = dao.fetchDeets(first, last, ssn);

        }catch (SQLException | IOException | PersonDontExistsException e) {
            System.out.println(e.getMessage());
            MemberMenu.member(user);
        }
    }
}
