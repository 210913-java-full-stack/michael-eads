package Menus;

import Exceptions.SsnErrorException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import static utils.Reformat.Manipulation;

public class MainMenu {
    public static void mainMenu() throws SsnErrorException, SQLException, IOException {
        clearScreen();

        Scanner incoming = new Scanner(System.in);
        System.out.println("Welcome to First Mikey's Bank and Trust\n\nHow can we assist you today?" +
                "\nLogin          (L) \nCreate Account (C) \nQuit           (Q)");
        String input = incoming.next();
        input = Manipulation(input);
        input = input.substring(0,1);
        if (input.equals("L"))
        {
            Login.login();
        }
        else if (input.equals("C"))
        {
            CreateUser.createUser();
        }
        else if (input.equals("Q"))
        {
            System.exit(0);
        }
        else
        {
            mainMenu();
        }
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

