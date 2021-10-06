package Exceptions;

import java.awt.*;

public class passwordFailedException extends Exception{
    public passwordFailedException()
    {
        super("Password failed, please try again.");
        //can I load a menu? or should I handle this elsewhere (login menu)
        //Also in the login menu, the member menu don't load
    }
}
