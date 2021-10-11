package Exceptions;

public class PersonDontExistsException extends Exception{

    public PersonDontExistsException()

    {
        super ("I'm sorry, that name doesn't exist.\nPlease have them come in and create an account with us.");
    }

}
