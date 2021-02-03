package com.lcs_server_st.models;
/*
    general busniess logic for response,
    where value is the stringified body
 */
public class Result
{
    private int code;
    private String value;
    private String type;

    public Result(){}
    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
