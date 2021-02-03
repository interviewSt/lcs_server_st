package com.lcs_server_st.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/*
    POD for response body for lcs requests
 */
public class LcsResponse
{

    private List<Value> lcs;

    public List<Value> getLcs()
    {
        return lcs;
    }

    public void setLcs(List<Value> lcs)
    {
        this.lcs = lcs;
    }

    //converts a list of strings into List<Value>, also removes duplicates
    public void addLcsFromStringList(List<String> lcs)
    {
        this.lcs.addAll( lcs.stream()
                .distinct()
                .sorted()
                .map(Value::new)
                .collect(Collectors.toList()));
    }

    @JsonIgnore
    public void addPair(String lcsValue)
    {
        lcs.add(new Value(lcsValue));
    }

    @JsonIgnore
    public LcsResponse()
    {
        lcs = new LinkedList<>();
    }

    static class Value
    {
        private String value;

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        @JsonIgnore
        Value(String value)
        {
            this.value = value;
        }
    }
}
