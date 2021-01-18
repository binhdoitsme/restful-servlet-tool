package com.hanu.draftapp.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Converter {
    private static final Map<String, Converter> converters = new HashMap<String, Converter>() {
        private static final long serialVersionUID = 1L;
        {
            put("json", new JsonConverter());
        }
    };
    public static Converter getInstance(String name) {
        return converters.get(name);
    }

    private final String name;

    public Converter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract <T> String forwardConvert(T item);
    public abstract <T> T backwardConvert(Class<T> clazz, String str);
}
