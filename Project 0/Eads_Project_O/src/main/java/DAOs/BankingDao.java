package DAOs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Exceptions.*;
import models.Balance;
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
    public int getAcc2(int acc, int id) throws WrongBankingTypeException {
        String sId;
        String stringId = Integer.toString(acc);

        String sAcc = stringId.substring(0,3);
        String type = stringId.substring(3);
        if(Integer.parseInt(type) ==id) {
             sId = Integer.toString(id+10);
        }
        else{
            sId = Integer.toString(id);
        }
        stringId = sAcc.concat(sId);
        int newId = Integer.parseInt(stringId);
        return newId;
    }
//create
    @Override
    public boolean newCust(String first, String last, String pw, int ss) throws SQLException {
        Connection conn = null;
        int newId = getNewAcc();

        try {
            conn = ConnectionManager.getConnection();
            String insertStatement = "INSERT INTO accounts_customers (last_four_ss, account_id) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
            preparedStatement.setInt(1, ss);
            preparedStatement.setInt(2, newId);
            preparedStatement.executeUpdate();
            //insert name password and social into table "customers"
            //Name verification should have already been run
            insertStatement = "INSERT INTO customers (first_name, last_name, password, last_four_ss) VALUES (?,?,?,?)";
            PreparedStatement preparedInsertStatement = conn.prepareStatement(insertStatement);
            preparedInsertStatement.setString(1, first);
            preparedInsertStatement.setString(2, last);
            preparedInsertStatement.setString(3, pw);
            preparedInsertStatement.setInt(4, ss);
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
        int acc = getAcc(ss);
        int newAcc = getAcc2(acc, id);
        //System.out.println(newAcc);

        conn = ConnectionManager.getConnection();

        String insertStatement = "INSERT INTO accounts_customers (last_four_ss, account_id) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
        preparedStatement.setInt(1, ss);
        preparedStatement.setInt(2, newAcc);
        preparedStatement.executeUpdate();

        String insertStatement2 = "INSERT INTO accounts (account_id, balance) VALUES (?,?)";
        PreparedStatement preparedInsertStatement = conn.prepareStatement(insertStatement2);
        preparedInsertStatement.setInt(1,newAcc);
        preparedInsertStatement.setDouble(2,money);
        preparedInsertStatement.executeUpdate();

        return true;

    }
//read
    @Override
    public ArrayList<Balance> accountBalance(int ss) throws SQLException {
        String getBalance = "SELECT * FROM accounts a JOIN accounts_customers ac ON a.account_id = ac.account_id WHERE last_four_ss = ?";
        PreparedStatement balanceStatement = conn.prepareStatement(getBalance);
        balanceStatement.setInt(1,ss);
        ResultSet rs = balanceStatement.executeQuery();
        ArrayList<Balance> accounts = new ArrayList<>();
        while(rs.next()){
            Balance newBalance = new Balance(rs.getInt("account_id"),rs.getDouble("balance"));
            accounts.add(newBalance);
        }return accounts;
    }


    @Override
    public boolean custName(String fName, String lName) throws SQLException, PersonExistsException {
        //checks to see if the person exists, if they do, throw exception
        String nameVer = "SELECT * FROM customers WHERE (first_name = ?) AND (last_name = ?)";
        PreparedStatement nameStatement = conn.prepareStatement(nameVer);
        nameStatement.setString(1,fName);
        nameStatement.setString(2,lName);
        ResultSet exist = nameStatement.executeQuery();

        if (exist.next()){
            throw new PersonExistsException();
        }
        return false;
    }

    @Override
    public boolean passVer(String first, String last, String pw) throws SQLException, passwordFailedException {
        //Checks the password given against the DB
        String passVer = "SELECT * FROM customers WHERE (first_name = ?) AND (last_name = ?) AND (password = ?)";
        PreparedStatement pwStatement = conn.prepareStatement(passVer);
        pwStatement.setString(1, first);
        pwStatement.setString(2, last);
        pwStatement.setString(3, pw);
        ResultSet exist = pwStatement.executeQuery();

        if (exist.next()){
            return true;
        }
        else {
            throw new passwordFailedException();
        }
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
    public int getSSN(String first,String last)throws SQLException{
        String statement = "SELECT last_four_ss FROM customers WHERE (first_name = ?) AND (last_name = ?)";
        PreparedStatement getSSN = conn.prepareStatement(statement);
        getSSN.setString(1,first);
        getSSN.setString(2,last);
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

        }return -1;
    }

    @Override
    public int getAcc(int ss) throws SQLException {
        int acc;
        String statement = "SELECT account_id FROM accounts_customers ac WHERE last_four_ss = ?";
        PreparedStatement account = conn.prepareStatement(statement);
        account.setInt(1,ss);
        ResultSet rs = account.executeQuery();
        if(rs.next()){
            acc = rs.getInt("account_id");
            return acc;
        }return -1;
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
