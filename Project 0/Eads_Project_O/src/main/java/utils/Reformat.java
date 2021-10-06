package utils;

import Exceptions.SsnErrorException;
import Menus.CreateUser;
import Menus.CreateWithDep;

import java.io.IOException;
import java.sql.SQLException;

public class Reformat
{
    //This method takes a string, separates it, capitalizes the first letter of each word, and returns it
    public static String Manipulation(String given)
    {
        String[] arr = given.split(" ");

        //StringBuffer is a peer class of String that provides much of the functionality of strings.
        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < arr.length; i++)
        {
            buff.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1).toLowerCase()).append(" ");
        }
        //System.out.println(buff.toString().trim());
        return buff.toString().trim();
    }

    public static int ssnVerification (String given) throws SsnErrorException {
        int length = given.length();
        int ss = 0;
        if(length != 4)
        {
            ss = 1;
        }
        else
        {
            if (given.matches("[0-9]*"))
            {
            ss = Integer.parseInt(given);
            //return ss;
            }
            else
            {
                ss=0;
            }
        }
        return ss;
    }
    public static boolean negCheck(String input){
        if(input.indexOf('-')>=0){
            return true;
        }
        return false;
    }
    public static double depWithVer(String input){
        double money = 0;
        //Check for "$" symbol
        if (input.indexOf('$')==0)
        {
            input = input.substring(1);
        }
        int length = input.length();
        if ((input.indexOf('.')) != (length -3) && (input.indexOf('.')) != -1)
        {
            System.out.println("Input invalid");
        }
        else
        {
            money = Double.parseDouble(input);
        }
        return money;
    }
    public static int accountCheck (String input, String choice, String first, String last, String pw, int ss) throws SQLException, IOException {
        int accNum= 0;
        if (!input.matches("[0-9]*")){
            System.out.println("I'm sorry the account number must consist of numbers.");
            CreateWithDep.createWithDep(input, first, last, pw, ss);
        }
        else if (input.length()!= 5){
            System.out.println("The account number is only five digits long.");
            CreateWithDep.createWithDep(choice, first, last, pw, ss);
        }
        return accNum;
    }
}
