package com.lcs_server_st.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.lcs_server_st.models.requests.LcsRequest;
import com.lcs_server_st.models.responses.LcsResponse;
import com.lcs_server_st.models.Result;
import com.lcs_server_st.util.LcsRequestException;
import com.lcs_server_st.util.RequestCompletionException;

import java.util.*;
/*
    handles the /lcs request
    attempts first map the request body into a valid form
    returns proper code on failure
*/
public class LcsController
{
    private ObjectMapper objectMapper;

    public LcsController()
    {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true);
    }

    public Result handle(String bodyRaw) throws RequestCompletionException
    {

        LcsRequest body;
        Result result = new Result();

        try
        {
            body = objectMapper.readValue(bodyRaw, LcsRequest.class);

            LcsResponse response = calculateLcs(curateBody(body));

            result.setCode(200);
            result.setValue( objectMapper.writeValueAsString(response) );

        }
        catch (LcsRequestException le)
        {
            throw new RequestCompletionException(
                    le.getLcsMessage(),
                    400);
        }
        catch (UnrecognizedPropertyException eb)
        {
            throw new RequestCompletionException(
                    String.format("Unrecognized field '%s'", eb.getPropertyName()),
                    422);
        }
        catch (JsonParseException | JsonMappingException em)
        {
            throw new RequestCompletionException(
                    "Malformed request body",
                    400);
        }
        catch (Exception e)
        {
            if (e.getClass() == RequestCompletionException.class)
            {
                throw (RequestCompletionException) e;
            }

            e.printStackTrace();

            throw new RequestCompletionException(
                    "whoops, something went wrong",
                    500);
        }

        return result;
    }

    //remove any white space characters from strings, throw lcs exception if body is has incorrect fields
    private LinkedList<String> curateBody(LcsRequest body) throws LcsRequestException
    {
        LinkedList<String> strings = body.getAsStrings();

        if(strings == null)
        {
            throw new LcsRequestException("Must contain field 'setOfStrings'");
        }
        else if(strings.isEmpty())
        {
            throw new LcsRequestException("'setOfStrings' cannot be empty");
        }

        ListIterator<String> i = strings.listIterator();
        String s;
        while (i.hasNext())
        {
            s = i.next().replaceAll("\\s","");
            i.set(s);
        }

        return strings;
    }

    //walks string array to determine the lcs
    private LcsResponse calculateLcs(LinkedList<String> strings)
    {
        LcsResponse response = new LcsResponse();

        if(strings.size() == 1)
        {
            response.addPair(strings.pop());
        }
        else if(strings.size() == 0)
        {
            return response;
        }
        else
        {
            String refrence = strings.pop();
            String subString;
            LinkedList<String> lcs = new LinkedList<>();

            //use caterpillar method to search for substrings
            for(int start = 0; start < refrence.length(); start++)
            {
                for(int end = start+1; end <= refrence.length(); end++)
                {
                    subString = refrence.substring(start, end);

                    if(isLcs(subString, strings))
                    {
                        if(lcs.isEmpty())
                        {
                            lcs.add(subString);
                            continue;
                        }

                        //compare the length of the current lcs to those already in
                        switch (Integer.compare(subString.length(), lcs.peek().length()))
                        {
                            case 1:
                                lcs.clear();

                            case 0:
                                lcs.add(subString);
                                break;

                            default:
                                break;
                        }
                    }
                }
            }

            response.addLcsFromStringList(lcs);
        }

        return response;
    }

    //determine if substring exists in all strings
    private boolean isLcs(String substring, LinkedList<String> strings)
    {
        for(String s : strings)
        {
            if(!s.contains(substring))
            {
                return false;
            }
        }

        return true;
    }
}
