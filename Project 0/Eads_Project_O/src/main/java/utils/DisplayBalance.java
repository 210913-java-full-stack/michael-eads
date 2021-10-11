package utils;

import DAOs.BankingDao;
import models.Balance;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DisplayBalance {
    /*
     * Here we get the user and use their SSN to display their accounts and balances.
     */
    public static void balance(User user){
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);

            ArrayList<Balance> balanceList;
            balanceList = dao.accountBalance(user.getSSN());
            String header = "\nAcc-Num| Balance\n-------|--------";
            System.out.println(header);
            for (int i = 0; i < balanceList.size(); i++) {
                printFormat(balanceList.get(i));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void printFormat(Balance balance) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(balance.getBalance());
        String brackets = " " + balance.getAccountNum() + " | ";
        System.out.print(brackets);
        System.out.println(moneyString);

        //System.out.printf("$%.2f\n", balance.getBalance());
    }
}
