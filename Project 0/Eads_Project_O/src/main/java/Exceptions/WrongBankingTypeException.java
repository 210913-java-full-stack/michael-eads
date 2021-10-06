package Exceptions;

public class WrongBankingTypeException extends Exception {
    public WrongBankingTypeException()
    {
        super ("That is a bad account type.");

    }
}