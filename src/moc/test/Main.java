package moc.test;

import com.as.InvalidMethodCallException;
import com.as.InvalidResponseException;
import com.as.RandomGenerator;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.IOException;

/**
 * Created by Arkadeep on 03-Jul-17.
 */
public class Main
{
    public static void main(String args[]) {
        int number=0, bitsLeft, bitsUsed, requestsLeft;
        //RandomGenerator rg = new RandomGenerator("28b82526-1052-4962-b569-2dc7590ca5d");
        try
        {
            // checked exception InvalidResponseException
            RandomGenerator rg = new RandomGenerator("28b82526-1052-4962-b569-2dc7590ca5d4");
            //rg.randomNumberGenerate(1, 10, 0, false,"idk");

            // unchecked exception InvalidMethodCallException
            number = rg.getElement(0);
            bitsLeft=rg.getBitsLeft();
            bitsUsed=rg.getBitsUsed();
            requestsLeft=rg.getRequestsLeft();
            System.out.println("Random Number = " + number);
            System.out.println("Bits Used = " + bitsUsed);
            System.out.println("Bits Left = " + bitsLeft);
            System.out.println("Requests Left = " + requestsLeft);
        }
//        catch (InvalidResponseException invalidResponseException)
//        {
//            System.out.println(invalidResponseException.getMessage());
//        }
        catch(InvalidMethodCallException invalidMethodCallException)
        {
            System.out.println(invalidMethodCallException.getMessage());
            invalidMethodCallException.printStackTrace();
        }
    }
}