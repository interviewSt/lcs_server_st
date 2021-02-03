package com.lcs_server_st.util;

import java.util.Date;

public class RequestCompletionException extends Exception
{
    private Date timestamp;
    private String errorMessage;
    private int code;

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public RequestCompletionException(String message, int code)
    {
        this.errorMessage = message;
        this.timestamp = new Date();
        this.code = code;
    }
}
