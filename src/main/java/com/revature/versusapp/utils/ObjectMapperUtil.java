package com.revature.versusapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
    // singleton: private constructor, public static synchronized getter method
    private static ObjectMapper objMapper;
    
    static {
        objMapper = null;
    }

    private ObjectMapperUtil() {
    }

    // factory: creates Connection objects and returns them
    public ObjectMapper getgetObjectMapper() {
        if ( objMapper == null ) {
            objMapper = new ObjectMapper();
        }
        
        return objMapper;
    }
}
