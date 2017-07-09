package com.github.iarks.RandomOrgAPI;

/**
 * Created by Arkadeep on 04-Jul-17.
 */
class InvalidResponseException extends Exception
{

    InvalidResponseException(String message, Throwable cause)
    {
        super(message,cause);
    }
    InvalidResponseException(String message)
    {
        super(message);
    }

}