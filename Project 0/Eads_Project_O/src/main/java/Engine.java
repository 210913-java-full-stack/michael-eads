import Exceptions.SsnErrorException;
import MenuPages.MainMenu;

import java.io.IOException;
import java.sql.SQLException;

public class Engine {
    public static void main(String args[]) throws SsnErrorException, SQLException, IOException {
        MainMenu.mainMenu();
//VVVVVVVVVVVVVVVVVVVVVVVVV--DEBUGING--VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

//        double money = 20.25;
//        int ss = 4561;
//        int id = 1;
//        try {
//            Connection conn = ConnectionManager.getConnection();
//            BankingDao dao = new BankingDao(conn);
//            int bal = dao.getAcc(ss, id);
//            System.out.println(bal);
//        } catch (SQLException | IOException e) {
//            System.out.println(e.getMessage());
//        }
    }
}