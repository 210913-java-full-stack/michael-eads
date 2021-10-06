package Exceptions;

import Menus.CreateUser;

import static Menus.CreateUser.createUser;

public class SsnErrorException extends Exception{
    public SsnErrorException()
    {
        super("I'm sorry, we only need the last four numbers of your Social Security Number." +
                "\n It also can ONLY be a number.");
    }
}
