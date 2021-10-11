import Exceptions.PersonDontExistsException;
import Exceptions.SsnErrorException;

import MenuPages.MainMenu;

import java.io.IOException;
import java.sql.SQLException;

public class Engine {
    /*
     * The start of all the fun. Also, where I debugged a lot of code pieces.
     * I should have every menu return here before going to the next.
     */
    public static void main(String args[]) throws SsnErrorException, SQLException, IOException, PersonDontExistsException {
        MainMenu.mainMenu();
    }
}