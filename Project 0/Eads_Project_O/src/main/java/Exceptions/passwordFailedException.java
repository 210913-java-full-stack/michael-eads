package Exceptions;

import java.awt.*;

public class passwordFailedException extends Exception{
    public passwordFailedException()
    {
        super("Password failed, please try again.");
    }
}
