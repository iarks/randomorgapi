package com.github.iarks.RandomOrgAPI;

/**
 * Created by Arkadeep on 04-Jul-17.
 */
public class InvalidResponseException extends Exception
{

    public InvalidResponseException(String message, Throwable cause)
    {
        super(message,cause);
    }
    public InvalidResponseException(String message)
    {
        super(message);
    }

}