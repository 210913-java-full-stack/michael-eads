package MenuPages;

import DAOs.BankingDao;
import Exceptions.PersonDontExistsException;
import Exceptions.SsnErrorException;
import ValidationVerification.AccountConfirmation;
import ValidationVerification.AccountValidation;
import ValidationVerification.DepWithValidation;
import models.User;
import utils.ConnectionManager;
import utils.DepositWithdraw;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import static utils.ConnectionManager.conn;
import static utils.Reformat.Manipulation;

public class TransferFunds {
    /*
     * Just like it sounds. We get the name and account number that the
     * user wants to transfer to, then the account number they want to
     * transfer FROM, and then transfer.
     */
    public static void transferMenu(User user) throws PersonDontExistsException, SQLException, SsnErrorException, IOException {
        Scanner incoming = new Scanner(System.in);
        boolean validated = false;int acc=0;int acc2=0;
        conn = ConnectionManager.getConnection();
        BankingDao dao = new BankingDao(conn);

        System.out.println("What is the first and last name of the person you wish to send " +
                "money too?\nFirst: ");
        String input = incoming.next();
        String first = Manipulation(input);
        System.out.println("Last: ");
        input = incoming.next();
        String last = Manipulation(input);
        System.out.println("The number of the account you wish to transfer TO? " +
                "\nAccount " +
                "Number:  ");
        input = incoming.next();
        validated = AccountValidation.accountCheck(input);
        if (validated == true) {
            acc = Integer.parseInt(input);
            AccountConfirmation.accCon2(first, last, acc, user);
        }
        System.out.println("Now, what account (of yours) do you wish to transfer FROM?" +
                "\nAccount Number:  ");
        input = incoming.next();

        validated = AccountValidation.accountCheck(input);
        if (validated == true) {
            acc2 = Integer.parseInt(input);
            AccountConfirmation.accCon(acc2, user);
        }
        System.out.println("How much do you want to transfer? \n");
        double money = -1;
        while (money == -1) {
            input = incoming.next();
            money = DepWithValidation.depWithVal(input);
        }
        try {
            if (dao.checkBal(acc2) >= money) {
                double money1 = dao.checkBal(acc2) - money;
                DepositWithdraw.moveIt(acc2, money1, user);
                double money2 = dao.checkBal(acc) + money;
                DepositWithdraw.moveIt(acc, money2, user);
            } else {
                System.out.println("Sorry, that amount exceeds your available balance.");
                MemberMenu.member(user);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        MemberMenu.member(user);

    }
}
