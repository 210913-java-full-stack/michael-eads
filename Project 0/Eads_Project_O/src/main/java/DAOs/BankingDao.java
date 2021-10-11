package DAOs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Exceptions.*;
import models.Balance;
import models.User;
import utils.ConnectionManager;

public class BankingDao implements bankingCrud {
    private Connection conn;

    public BankingDao(Connection conn){
        this.conn = conn;
    }
    public int getNewAcc() throws SQLException {
        int oldId = newAccNum(); String sId="01";
        oldId = oldId+10000;
        String stringId = Integer.toString(oldId);
        String acc = stringId.substring(0,3);
        stringId = acc.concat(sId);
        int newId = Integer.parseInt(stringId);
        return newId;
    }

//create
    @Override
    public boolean newCust(User user) throws SQLException {
        Connection conn = null;
        int newId = getNewAcc();

        try {
            conn = ConnectionManager.getConnection();
            String insertStatement = "INSERT INTO accounts_customers (last_four_ss, account_id) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
            preparedStatement.setInt(1, user.getSSN());
            preparedStatement.setInt(2, newId);
            preparedStatement.executeUpdate();

            insertStatement = "INSERT INTO customers (first_name, last_name, password, last_four_ss) VALUES (?,?,?,?)";
            PreparedStatement preparedInsertStatement = conn.prepareStatement(insertStatement);
            preparedInsertStatement.setString(1, user.getFirst_name());
            preparedInsertStatement.setString(2, user.getLast_name());
            preparedInsertStatement.setString(3, user.getPassword());
            preparedInsertStatement.setInt(4, user.getSSN());
            preparedInsertStatement.executeUpdate();

            String insertStatement2 = "INSERT INTO accounts (account_id, balance) VALUES (?,?)";
            PreparedStatement preparedStmnt = conn.prepareStatement(insertStatement2);
            preparedStmnt.setInt(1,newId);
            preparedStmnt.setDouble(2,0.0);
            preparedStmnt.executeUpdate();
        }
        catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean newAcc(int ss, int id, double money) throws SQLException, WrongBankingTypeException, IOException {
        int acc = getAcc(ss, id);

        conn = ConnectionManager.getConnection();

        String insertStatement = "INSERT INTO accounts_customers (last_four_ss, account_id) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
        preparedStatement.setInt(1, ss);
        preparedStatement.setInt(2, acc);
        preparedStatement.executeUpdate();

        String insertStatement2 = "INSERT INTO accounts (account_id, balance) VALUES (?,?)";
        PreparedStatement preparedInsertStatement = conn.prepareStatement(insertStatement2);
        preparedInsertStatement.setInt(1,acc);
        preparedInsertStatement.setDouble(2,money);
        preparedInsertStatement.executeUpdate();
        return true;

    }

    @Override
    public void linkThem(int ss, int acc) throws SQLException {
        String insertStatement = "INSERT INTO accounts_customers (last_four_ss, account_id) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
        preparedStatement.setInt(1, ss);
        preparedStatement.setInt(2, acc);
        preparedStatement.executeUpdate();
    }

    //read
    @Override
    public ArrayList<Balance> accountBalance(int ss) throws SQLException {
        String getBalance = "SELECT * FROM accounts a JOIN accounts_customers ac " +
                "ON a.account_id = ac.account_id WHERE last_four_ss = ?";
        PreparedStatement balanceStatement = conn.prepareStatement(getBalance);
        balanceStatement.setInt(1,ss);
        ResultSet rs = balanceStatement.executeQuery();
        ArrayList<Balance> accounts = new ArrayList<>();
        while(rs.next()){
            Balance newBalance = new Balance(rs.getInt("account_id"),rs.getDouble("balance"));
            accounts.add(newBalance);
        }
        return accounts;
    }

    @Override
    public boolean custName(int ss) throws SQLException, PersonExistsException {
        String nameVer = "SELECT * FROM customers WHERE (last_four_ss = ?)";
        PreparedStatement nameStatement = conn.prepareStatement(nameVer);
        nameStatement.setInt(1,ss);
        ResultSet exist = nameStatement.executeQuery();
        if (exist.next()){
            return true;
        }
        return false;
    }

    @Override
    public String passVer(User user, String pw) throws SQLException, passwordFailedException {
        //Checks the password given against the DB
        String passVer = "SELECT * FROM customers WHERE (first_name = ?) AND (last_name = ?) AND (password = ?)";
        PreparedStatement pwStatement = conn.prepareStatement(passVer);
        pwStatement.setString(1, user.getFirst_name());
        pwStatement.setString(2, user.getLast_name());
        pwStatement.setString(3, pw);
        ResultSet rs = pwStatement.executeQuery();
        if (rs.next()){
            String pwFile = rs.getString("password");
            return pwFile;
        }
        return "";
    }

    @Override
    public int newAccNum() throws SQLException {
        String statement = "SELECT MAX(account_id) FROM accounts";
        PreparedStatement findAccNum = conn.prepareStatement(statement);
        ResultSet rs = findAccNum.executeQuery();
        if(rs.next()) {
            int aNum = rs.getInt("MAX(account_id)");
            return aNum;
        }return -1;
    }

    @Override
    public int getSSN(User user)throws SQLException{
        String statement = "SELECT last_four_ss FROM customers WHERE (first_name = ?) AND " +
                "(last_name = ?) AND (password = ?)";
        PreparedStatement getSSN = conn.prepareStatement(statement);
        getSSN.setString(1,user.getFirst_name());
        getSSN.setString(2,user.getLast_name());
        getSSN.setString(3,user.getPassword());
        ResultSet rs = getSSN.executeQuery();
        if(rs.next()){
            int ssn = rs.getInt("last_four_ss");
            return ssn;
        }return -1;
    }

    @Override
    public double checkBal(int id) throws SQLException {
        String statement = "SELECT balance FROM accounts a WHERE account_id = ?";
        PreparedStatement checkBal = conn.prepareStatement(statement);
        checkBal.setInt(1,id);
        ResultSet rs = checkBal.executeQuery();
        while (rs.next()){
            double bal = rs.getDouble("balance");
            return bal;
        }
        return -1;
    }

    @Override
    public int getAcc(int ss, int id) throws SQLException {
        int acc = 0;
        String statement = "";
        if(id == 1) {
            statement = "SELECT MAX (account_id) FROM accounts_customers ac " +
                    "WHERE last_four_ss = ? AND account_id LIKE '%1'";
        }
        else{
            statement = "SELECT MAX (account_id) FROM accounts_customers ac " +
                    "WHERE last_four_ss = ? AND account_id LIKE '%2'";
        }
        PreparedStatement account = conn.prepareStatement(statement);
        account.setInt(1,ss);
        ResultSet rs = account.executeQuery();
        if(rs.next()) {
            acc = rs.getInt("max (account_id)");
            int alteredAcc = (acc + 10);
            if (acc == 0) {
                statement = "SELECT MAX (account_id) FROM accounts_customers ac " +
                        "WHERE last_four_ss = ? AND account_id LIKE '%1'";
                account = conn.prepareStatement(statement);
                account.setInt(1, ss);
                rs = account.executeQuery();
                if (rs.next()) {
                    acc = rs.getInt("max (account_id)");
                    alteredAcc = (acc + 1);
                }
            }
            return alteredAcc;

        }return -1;
    }

    @Override
    public User fetchDeets(String first, String last, int ssn) throws SQLException, PersonDontExistsException {
        String getBalance = "SELECT * FROM customers c WHERE (first_name = ?) AND (last_name = ?) AND last_four_ss = ?";
        PreparedStatement balanceStatement = conn.prepareStatement(getBalance);
        balanceStatement.setString(1,first);
        balanceStatement.setString(2,last);
        balanceStatement.setInt(3,ssn);
        ResultSet rs = balanceStatement.executeQuery();
        if(rs.next()){
            User user2 = new User(rs.getInt("last_four_ss"),rs.getString("first_name"),rs.getString("last_name"));
            return user2;
        }
        else
        {
            throw new PersonDontExistsException();
        }
    }

    @Override
    public int fetchDeets2(String first, String last, int acc) throws SQLException, PersonDontExistsException {
        String getBalance = "SELECT c.last_four_ss FROM customers c " +
                "JOIN accounts_customers ac ON c.last_four_ss = ac.last_four_ss\n" +
                "JOIN accounts a ON ac.account_id = a.account_id WHERE first_name = ? " +
                "AND last_name = ? AND a.account_id = ?";
        PreparedStatement balanceStatement = conn.prepareStatement(getBalance);
        balanceStatement.setString(1,first);
        balanceStatement.setString(2,last);
        balanceStatement.setInt(3,acc);
        ResultSet rs = balanceStatement.executeQuery();
        if(rs.next()) {
            int ssn = rs.getInt("last_four_ss");
            return ssn;
        }
        else {
            throw new PersonDontExistsException();
        }
    }

    //update
    @Override
    public boolean depWith(int acc, double money) throws SQLException {
        //System.out.println("Made it to depWith");
        String statement = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        PreparedStatement deposit = conn.prepareStatement(statement);
        deposit.setDouble(1, money);
        deposit.setInt(2,acc);
        deposit.executeUpdate();
        return true;
    }

    //delete
    @Override
    public boolean closeAccount(int id) throws SQLException {
        return false;
    }
}
