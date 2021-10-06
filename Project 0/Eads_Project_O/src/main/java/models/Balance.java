package models;

public class Balance {
    private int accountNum;
    private double balance;

    public Balance(int accountNum, double balance){
        this.accountNum = accountNum;
        this.balance = balance;
    }
    public int getAccountNum(){
        return accountNum;
    }
    public double getBalance(){
        return balance;
    }
}
