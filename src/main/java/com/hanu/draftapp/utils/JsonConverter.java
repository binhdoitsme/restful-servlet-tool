package com.hanu.draftapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter extends Converter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public JsonConverter() {   
        super("json");
    }
    
    @Override
    public <T> String forwardConvert(T item) {
        try { return objectMapper.writeValueAsString(item); }
        catch (JsonProcessingException ex) { throw new RuntimeException(ex); }
    }

    @Override
    public <T> T backwardConvert(Class<T> clazz, String str) {
        try { return objectMapper.readValue(str, clazz); }
        catch (JsonProcessingException ex) { throw new RuntimeException(ex); }
    }
}
