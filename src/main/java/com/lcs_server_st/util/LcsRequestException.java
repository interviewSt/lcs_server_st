package com.lcs_server_st.util;

public class LcsRequestException extends Exception
{
    private String lcsMessage;

    public String getLcsMessage()
    {
        return lcsMessage;
    }

    public void setLcsMessage(String lcsMessage) {
        this.lcsMessage = lcsMessage;
    }

    public LcsRequestException(String lcsMessage)
    {
        this.lcsMessage = lcsMessage;
    }
}
