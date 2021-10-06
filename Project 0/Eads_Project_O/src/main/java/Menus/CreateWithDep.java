package Menus;

import DAOs.BankingDao;
import Exceptions.WrongBankingTypeException;
import models.Balance;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static utils.Reformat.*;

public class CreateWithDep {

    private static Connection conn;

    public static void createWithDep(String input, String first, String last, String pw, int ss) throws SQLException, IOException {
        Scanner incoming = new Scanner(System.in);
        switch (input) {
            case "D"://deposit
                balance(ss);
                System.out.println("What account number did you want to deposit to?");
                input = incoming.next();
                int acc = accountCheck(input, "D", first, last, pw, ss);
                System.out.println("And how much are we depositing today?");
                input = incoming.next();
                if(negCheck(input)){
                    System.out.println("You cannot deposit a negative amount.");
                    createWithDep("D", first,last,pw,ss);
                }
                else {
                    double money = depWithVer(input);
                    try {
                        Connection conn = ConnectionManager.getConnection();
                        BankingDao dao = new BankingDao(conn);

                        money = (dao.checkBal(acc) + money);
                        System.out.println(money);

                        if (dao.depWith(acc, money)) {
                            balance(ss);
                            MemberMenu.member(first, last, pw);
                        }
                    } catch (SQLException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            case "W"://withdraw
                balance(ss);
                System.out.println("What account number did you want to withdraw to?");
                input = incoming.next();
                acc = accountCheck(input, "W", first, last, pw, ss);
                System.out.println("And how much are we withdrawing today?");

                Scanner incoming2 = new Scanner(System.in);
                input = incoming.next();
                int input2 = Integer.parseInt(input);
                if(input2 < 0) {
                    System.out.println("You cannot deposit a negative amount.");
                    break;
                }

//                if(negCheck(input)){
//                    System.out.println("You cannot deposit a negative amount.");
//                    createWithDep("D", first,last,pw,ss);
//                }
                else {
                    double money = depWithVer(input);
                    try {
                        Connection conn = ConnectionManager.getConnection();
                        BankingDao dao = new BankingDao(conn);
                        //double bal = dao.checkBal(acc);
                        System.out.println(dao.checkBal(acc));
                        //System.out.println("bal is "+bal);
                        if (dao.checkBal(acc) <= money) {
                            money = dao.checkBal(acc) - money;
                            if (dao.depWith(ss, money)) {
                                balance(ss);
                                MemberMenu.member(first, last, pw);
                            }
                        } else {
                            System.out.println("Sorry, that amount exceeds your available balance.");
                            createWithDep("W", first, last, pw, ss);
                        }
                    } catch (SQLException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            case "C": //create a new account
                System.out.println("What type of account do you want to create?\n" +
                        "C) Checking\nS) Savings\n");
                input = incoming.next();
                input = Manipulation(input);
                input = input.substring(0,1);
                int id = 0;
                if(input.equals("C")){
                    id = 11;
                }
                else if(input.equals("S")){
                id = 12;
                }
                else{
                    System.out.println("You must enter a \"C\" or a \"S\".");
                    createWithDep("A",first,last,pw,ss);
                }
                System.out.println("Now how much money are we starting this account with?");
                input = incoming.next();
                if(negCheck(input)){
                    System.out.println("You cannot deposit a negative amount.");
                    createWithDep("D", first,last,pw,ss);
                }
                else {
                    double money = depWithVer(input);
                    try {
                        Connection conn = ConnectionManager.getConnection();
                        BankingDao dao = new BankingDao(conn);

                        System.out.println("ID: "+id +" money: "+money);

                        if (dao.newAcc(ss, id, money)) {
                            MemberMenu.member(first, last, pw);
                        }
                    } catch (SQLException | IOException | WrongBankingTypeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
        }
    }
    public static void balance(int ss){
        try
        {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);

            ArrayList<Balance> balanceList;
            balanceList = dao.accountBalance(ss);
            String header = "\nAcc-Num| Balance\n";
            System.out.println(header);
            for(int i = 0; i<balanceList.size();i++)
            {
                MemberMenu.printFormat(balanceList.get(i));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
