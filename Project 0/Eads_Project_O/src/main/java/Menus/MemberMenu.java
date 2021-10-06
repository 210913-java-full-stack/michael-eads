package Menus;

import DAOs.BankingDao;
import models.Balance;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static utils.Reformat.Manipulation;

//Member menu will take the name and password from either create user menu or the
// log in menu. Display the menu, and then we will then go to the
// Create/Withdraw/Deposit menu.
public class MemberMenu {
    public static void member(String firstName, String lastName, String pass) throws SQLException, IOException {
        String first = firstName; String last = lastName; String pw = pass; int ss = 0; String input;

        //Getting the ss
        ss = getSSN(first, last);

        Scanner incoming = new Scanner(System.in);
        System.out.println("\n\nWhat would you like to do today?\nB)  View Accounts\n" +
                "D)  Make a Deposit\nW)  Make a Withdraw\nC)  Create a New Account");
        input = incoming.next();
        input = Manipulation(input);
        input = input.substring(0,1);
        if (input.equals("D")||input.equals("W")||input.equals("C"))
        {
            CreateWithDep.createWithDep(input, first, last, pw, ss);
        }
        else if (input.equals("B"))
        {
            try
            {
                Connection conn = ConnectionManager.getConnection();
                BankingDao dao = new BankingDao(conn);

                ArrayList<Balance> balanceList;
                balanceList = dao.accountBalance(ss);
                String header = "\nAcc-Num| Balance\n";
                for(int i = 0; i<balanceList.size();i++)
                {
                    printFormat(balanceList.get(i));
                }
            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
            MemberMenu.member(first, last, pw);
        }
        else if (input.equals("Q"))
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Please enter the letter \"D\", \"W\", \"C\" or \"Q\"");
            MemberMenu.member(first, last, pw);
        }

    }
    public static int getSSN(String first, String last){
        int ss = 0;
        try {
            Connection conn = ConnectionManager.getConnection();
            BankingDao dao = new BankingDao(conn);

            ss = dao.getSSN(first, last);
        }
        catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return ss;
    }
    public static void printFormat(Balance balance)
    {

        String brackets = " "+balance.getAccountNum() + " | " ;
        System.out.print(brackets);
        System.out.printf("$%.2f\n",balance.getBalance());
    }
}
