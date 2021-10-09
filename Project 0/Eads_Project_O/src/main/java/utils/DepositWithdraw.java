package utils;

import DAOs.BankingDao;
import MenuPages.MemberMenu;
import models.Balance;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepositWithdraw {

    /*
     * This was used twice so I moved it. It performs the action of depositing or withdrawing.
     */
    public static void moveIt(int acc, double money, User user) throws SQLException, IOException {
        Connection conn = ConnectionManager.getConnection();
        try {
            BankingDao dao = new BankingDao(conn);
            if (dao.depWith(acc, money)) {
                balance(user.getSSN());
                MemberMenu.member(user);
            }
        }catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void balance(int ss) {
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);

            ArrayList<Balance> balanceList;
            balanceList = dao.accountBalance(ss);
            String header = "\nAcc-Num| Balance";
            System.out.println(header);
            for (int i = 0; i < balanceList.size(); i++) {
                MemberMenu.printFormat(balanceList.get(i));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}