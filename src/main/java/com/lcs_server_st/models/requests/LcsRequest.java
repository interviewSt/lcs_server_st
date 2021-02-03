package com.lcs_server_st.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.stream.Collectors;
/*
    POD json structure for request structure for /lcs route
 */
public class LcsRequest
{
    private LinkedList<Value> setOfStrings;

    public LinkedList<Value> getSetOfStrings()
    {
        return setOfStrings;
    }

    public void setSetOfStrings(LinkedList<Value> setOfStrings)
    {
        this.setOfStrings = setOfStrings;
    }

    @JsonIgnore
    public LinkedList<String> getAsStrings()
    {
        return setOfStrings != null ?
                setOfStrings.stream().map(value -> value.getValue()).collect(Collectors.toCollection(LinkedList::new))
                : null;
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
            this.value = value != null ? value : "";
        }
    }
}
