import DAOs.BankingDao;
import Exceptions.SsnErrorException;
import Menus.MainMenu;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Engine {
    public static void main(String args[]) throws SsnErrorException, SQLException, IOException {
        MainMenu.mainMenu();
//VVVVVVVVVVVVVVVVVVVVVVVVV--DEBUGING--VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
//        int ss = 0;
//            try {
//                Connection conn = ConnectionManager.getConnection();
//                BankingDao dao = new BankingDao(conn);
//
//                ss = dao.getSSN("Jason", "Smith");
//                System.out.println(ss);
//
//            }
//            catch (SQLException | IOException e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println(ss);
    }
}