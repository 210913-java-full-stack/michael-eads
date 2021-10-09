package MenuPages;

import DAOs.BankingDao;
import Exceptions.WrongBankingTypeException;
import ValidationVerification.DepWithValidation;
import models.User;
import utils.ConnectionManager;
import utils.DepositWithdraw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static ValidationVerification.AccountValidation.accountCheck;
import static utils.DepositWithdraw.balance;
import static utils.Reformat.Manipulation;

public class CreateWithDep {
    private static Connection conn;

    /*
     * Create, Withdraw, Deposit, (CreateWithDep) does exactly what it sounds like. It takes the
     * users response and acts upon them. With each one, they are asked specifics and then that
     * info is ran to the database.
     */
    public static void createWithDep(String input, User user) throws SQLException, IOException {
        Scanner incoming = new Scanner(System.in);
        conn = ConnectionManager.getConnection();
        BankingDao dao = new BankingDao(conn);
        switch (input) {
            case "D"://deposit
                balance(user.getSSN());
                System.out.println("What account number did you want to deposit to?");
                input = incoming.next();
                int acc = accountCheck(input, "D", user);
                System.out.println("And how much are we depositing today?");
                double money = -1;
                while (money == -1) {
                    input = incoming.next();
                    money = DepWithValidation.depWithVal(input);
                }
                try {
                    money = dao.checkBal(acc) + money;
                    if (dao.depWith(acc, money)) {
                        balance(user.getSSN());
                        MemberMenu.member(user);
                    }
                } catch (SQLException | IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "W"://withdraw
                balance(user.getSSN());
                System.out.println("What account number did you want to withdraw to?");
                input = incoming.next();
                acc = accountCheck(input, "W", user);
                System.out.println("And how much are we withdrawing today?");
                money = -1;
                while (money == -1) {
                    input = incoming.next();
                    money = DepWithValidation.depWithVal(input);
                }
                try {
                    if (dao.checkBal(acc) >= money) {
                        money = dao.checkBal(acc) - money;
                        DepositWithdraw.moveIt(acc, money, user);
                    } else {
                        System.out.println("Sorry, that amount exceeds your available balance.");
                        createWithDep("W", user);
                    }
                } catch (SQLException | IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "C": //create a new account
                System.out.println("What type of account do you want to create?\n" +
                        "C) Checking\nS) Savings\n");
                input = incoming.next();
                input = Manipulation(input);
                input = input.substring(0, 1);
                int id = 0;
                if (input.equals("C")) {
                    id = 1;
                } else if (input.equals("S")) {
                    id = 2;
                } else {
                    System.out.println("You must enter a \"C\" or a \"S\".");
                    createWithDep("C", user);
                }
                System.out.println("Now how much money are we starting this account with?");
                money = -1;
                while (money == -1) {
                    input = incoming.next();
                    money = DepWithValidation.depWithVal(input);
                }
                money = DepWithValidation.depWithVal(input);
                try {
                    if (dao.newAcc(user.getSSN(), id, money)) {
                        MemberMenu.member(user);
                    }
                } catch (SQLException | IOException | WrongBankingTypeException e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
    }
}

