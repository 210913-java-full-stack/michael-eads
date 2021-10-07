package DAOs;

import Exceptions.PersonDontExistsException;
import Exceptions.PersonExistsException;
import Exceptions.WrongBankingTypeException;
import Exceptions.passwordFailedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface bankingCrud<M> {
//create
    //create customers, will need to call account_id to find out where we are on the number scheme
    public boolean newCust(String first, String last, String pw, int ss) throws SQLException;
    //create account, will need to call account_id to find out where we are on the number scheme
    public boolean newAcc(int ss, int id, double money) throws SQLException, WrongBankingTypeException, IOException;

//read
    //display all acounts with balance
    public ArrayList<M> accountBalance (int ss)throws SQLException;
    //return customer name forVer
    public boolean custName(String fName, String lName) throws SQLException, PersonDontExistsException, PersonExistsException;
    //return password from customer forVer
    public boolean passVer(String first, String last, String pw) throws SQLException, passwordFailedException;
    //return account number for incrimination
    public int newAccNum()throws SQLException;
    //return SSN for CreateWithDep
    public int getSSN(String first,String last)throws SQLException;
    //check balance before withdrawing
    public double checkBal(int id)throws SQLException;
    //retrieve existing account_id
    public int getAcc(int ss, int id)throws SQLException;

//update
    //deposit or withdraw funds
    public boolean depWith(int acc, double money)throws SQLException;

//delete
    //close account
    public boolean closeAccount(int id)throws SQLException;
    //delete customer?
}
