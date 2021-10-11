package utils;

import DAOs.BankingDao;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DepositWithdraw {

    /*
     * This was used twice so I moved it. It performs the action of depositing or withdrawing.
     */
    public static void moveIt(int acc, double money, User user) throws SQLException, IOException {
        Connection conn = ConnectionManager.getConnection();
        try {
            BankingDao dao = new BankingDao(conn);
            if (dao.depWith(acc, money)) {
                DisplayBalance.balance(user);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}