package com.lcs_server_st.models.config;

public class ConfigFile
{
    private Integer thread_count;
    private Integer port;

    public Integer getThread_count()
    {
        return thread_count;
    }

    public void setThread_count(Integer thread_count)
    {
        this.thread_count = thread_count;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }
}
