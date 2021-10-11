package DAOs;

import Exceptions.PersonDontExistsException;
import Exceptions.PersonExistsException;
import Exceptions.WrongBankingTypeException;
import Exceptions.passwordFailedException;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface bankingCrud<M> {
//create
    //create customers, will need to call account_id to find out where we are on the number scheme
    public boolean newCust(User user) throws SQLException;
    //create account, will need to call account_id to find out where we are on the number scheme
    public boolean newAcc(int ss, int id, double money) throws SQLException, WrongBankingTypeException, IOException;
    //link userA's account to userB (Share account)
    public void linkThem(int acc, int ss) throws SQLException;

//read
    //display all acounts with balance
    public ArrayList<M> accountBalance (int ss)throws SQLException;
    //return customer name forVer
    public boolean custName(int ss) throws SQLException, PersonDontExistsException, PersonExistsException;
    //return password from customer forVer
    public String passVer(User user, String pw) throws SQLException, passwordFailedException;
    //return account number for incrimination
    public int newAccNum()throws SQLException;
    //return SSN for CreateWithDep
    public int getSSN(User user)throws SQLException;
    //check balance before withdrawing
    public double checkBal(int id)throws SQLException;
    //retrieve existing account_id
    public int getAcc(int ss, int id)throws SQLException;
    //retrieve name, first and last
    public User fetchDeets (String first, String last, int ssn) throws SQLException, PersonDontExistsException;
    //retrieve first, last, and account id
    public int fetchDeets2 (String first, String last, int acc) throws SQLException, PersonDontExistsException;


//update
    //deposit or withdraw funds
    public boolean depWith(int acc, double money)throws SQLException;


//delete
    //close account
    public boolean closeAccount(int id)throws SQLException;
    //delete customer?
}
