package Exceptions;

public class PersonDontExistsException extends Exception{

    public PersonDontExistsException()

    {
        super ("I'm sorry, that name doesn't exist. please check spelling and try again.");
    }

}
