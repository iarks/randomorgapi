package com.github.iarks.RandomOrgAPI;

/**
 * Created by Arkadeep on 04-Jul-17.
 */
public class InvalidMethodCallException extends RuntimeException
{
    public InvalidMethodCallException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
