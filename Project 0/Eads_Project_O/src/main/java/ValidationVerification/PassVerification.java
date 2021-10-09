package ValidationVerification;

import DAOs.BankingDao;
import Exceptions.passwordFailedException;
import models.User;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class PassVerification {
    /*
     * PassVer checks the password that the user has submitted
     * against the password on file.
     */
    public static boolean passVer (User user, String pw) {
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);
            String pwFile = dao.passVer(user, pw);

            if (pwFile.equals(pw)) {
                return true;
            } else {
                System.out.println("Password failed, please try again.");
                return false;
            }
        } catch (SQLException | IOException | passwordFailedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}