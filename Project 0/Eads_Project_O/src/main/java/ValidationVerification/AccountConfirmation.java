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

public class AccountConfirmation {
    /*
     * accCon checks to ensure that the account number given, is in fact, the users
     * account.
     * accCon2 does the same, but for the transferee
     */
    public static void accCon(int acc, User user) throws SQLException, IOException, PersonDontExistsException, SsnErrorException {
        Connection conn = ConnectionManager.getConnection();

            BankingDao dao = new BankingDao(conn);
            int ss = (dao.fetchDeets2(user.getFirst_name(), user.getLast_name(), acc));
            if(ss <1){
                System.out.println("There was a problem processing your request.");
                MemberMenu.member(user);
        }
    }
    public static void accCon2 (String first, String last, int acc, User user) throws SQLException, IOException, PersonDontExistsException, SsnErrorException {
        Connection conn = ConnectionManager.getConnection();
        BankingDao dao = new BankingDao(conn);
        int ss = (dao.fetchDeets2(first, last, acc));
        if(ss <1){
            System.out.println("There was a problem processing your request.");
            MemberMenu.member(user);
        }
    }
}
