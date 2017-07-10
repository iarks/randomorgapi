/**
 * Created by Arkadeep on 02-Jul-17.
 */
package com.github.iarks.RandomOrgAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;


public class RandomNumber
{
    private String key;
    private JsonArray data;
    private JsonElement result;
    private int numberOfRandoms=0;

    public RandomNumber(String api_key)
    {
        key = "\"" + api_key + "\"";
    }

    public void generate(int n, int max, int min, boolean replacement, String id) throws InvalidResponseException
    {
        //Initialise payload
        String payload = "{\"jsonrpc\": \"2.0\", \"method\": \"generateIntegers\", \"params\": {\"apiKey\":" + key + ", \"n\":" + n + ",\"min\":" + min + ",\"max\": " + max + " ,\"replacement\": " + replacement + " },\"id\": \"" + id + "\"}";
        //System.out.println(payload);

        //Initialise other variables
        StringBuilder response = new StringBuilder();
        URL url;
        int flag=1;
        numberOfRandoms=n;

        HttpsURLConnection connection=null;
        BufferedReader br=null;

        //Establish Connection
        try
        {
            flag=1;
            url = new URL("https://api.random.org/json-rpc/1/invoke");
            //url = new URL("api.urbandictionary.com/v0/random");
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //add request header
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            //Write to OP stream
            OutputStream os = connection.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();


            //Read response
            flag=2;
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
            {
                response.append(line);
            }
        }
        catch (IOException ioexception)
        {
            if(flag==1)
            {
                throw new InvalidResponseException("Host Unreachable", ioexception);
            }
            else if (flag==2)
            {
                throw new InvalidResponseException("Null Response Received", ioexception);
            }

        }
        finally
        {
            if(br!=null)
            {
                try
                {
                    br.close();
                }
                catch (IOException io){}
            }
            if(connection!=null)
            {
                connection.disconnect();
            }

        }

        //otherwise parse the response into JSON data
        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(response.toString());
        //System.out.println("root = "+ root);

        //Extract Result from Root
        result = root.getAsJsonObject().get("result");
        if (result == null)//Result Section does not exist
        {
            //extract error message
            JsonElement error = root.getAsJsonObject().get("error");
            JsonElement message = error.getAsJsonObject().get("message");
            throw new InvalidResponseException(message.toString());
        }

        //otherwise extract required data
        JsonElement random = result.getAsJsonObject().get("random");
        JsonObject randomObj = (JsonObject) random;
        data = randomObj.get("data").getAsJsonArray();
        //System.out.println(data.get(0));
    }

    public int getElement(int index) throws InvalidMethodCallException
    {
        String dataItem;
        try
        {
            dataItem = (data.get(index)).toString();
        }
        catch (NullPointerException nullPointerException)
        {
            throw new InvalidMethodCallException("Index does not exist", nullPointerException);
        }
        return Integer.parseInt(dataItem);
    }

    public int[] getElementAsArray(int index) throws InvalidMethodCallException
    {
        int array[] = new int[numberOfRandoms];
        if (numberOfRandoms==0)
        {
            throw new InvalidMethodCallException("Index does not exist", null);
        }
        String dataItem;
        for (int i=0;i<numberOfRandoms;i++)
        {
            try
            {
                dataItem = (data.get(index)).toString();
            }
            catch (NullPointerException nullPointerException)
            {
                throw new InvalidMethodCallException("Index does not exist", nullPointerException);
            }
            array[i]=Integer.parseInt(dataItem);
        }
        return array;
    }

    public int getBitsUsed()throws InvalidMethodCallException
    {
        String bitsUsed;
        try
        {
            bitsUsed = (result.getAsJsonObject().get("bitsUsed")).toString();
        }
        catch(NullPointerException nullPointerException)
        {
            throw new InvalidMethodCallException("Bits Used does not exist", nullPointerException);
        }
        return Integer.parseInt(bitsUsed);
    }

    public int getBitsLeft()throws InvalidMethodCallException
    {
        String bitsLeft;
        try
        {
            bitsLeft = (result.getAsJsonObject().get("bitsLeft")).toString();
        }
        catch (NullPointerException nullPointerException)
        {
            throw new InvalidMethodCallException("Bits Left does not exist",nullPointerException);
        }
        return Integer.parseInt(bitsLeft);
    }

    public int getRequestsLeft()throws InvalidMethodCallException
    {
        String requestsLeft;
        try
        {
            requestsLeft = (result.getAsJsonObject().get("requestsLeft")).toString();
        }
        catch (NullPointerException nullPointerException)
        {
            throw new InvalidMethodCallException("Requests does not exist",nullPointerException);
        }
        return Integer.parseInt(requestsLeft);
    }
}