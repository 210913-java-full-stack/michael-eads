package Exceptions;

public class PersonExistsException extends Exception{
    public PersonExistsException()
    {
        super ("I'm sorry, that name already exist. please check spelling and try again.");
    }
}
