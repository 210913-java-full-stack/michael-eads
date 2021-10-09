package MenuPages;

import Exceptions.SsnErrorException;
import utils.WindowWasher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import static utils.Reformat.Manipulation;

public class MainMenu {
    /*
     * The very first menu the user will see, it welcomes the user and gives them a brief choice.
     * The input is sent through a process that capitalizes the first letter and makes all the rest
     * lowercase. Then the word is cut off after the first letter.
     */
    public static void mainMenu() throws SsnErrorException, SQLException, IOException {
        WindowWasher.clearScreen();

        Scanner incoming = new Scanner(System.in);
        System.out.println("Welcome to First Mikey's Bank and Trust\n<><><><><><><><><><>$" +
                "<><><><><><><><><>\n     How can we assist you today?      \n           L)" +
                " Login\n           C) Create Account\n           Q) Quit");
        String input = incoming.next();
        input = Manipulation(input);
        input = input.substring(0,1);
        if (input.equals("L")){
            Login.login();
        }
        else if (input.equals("C")){
            CreateUser.createUser();
        }
        else if (input.equals("Q")){
            System.exit(0);
        }
        else{
            mainMenu();
        }
    }
}

