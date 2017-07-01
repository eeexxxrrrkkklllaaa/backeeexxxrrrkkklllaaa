package com.prosport.place.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.io.IOException;

/**
 * @author Vlad Milyutin.
 */
public class JsonUtil {
    private JsonUtil(){    }

    static class DisablingJsonIgnore extends JacksonAnnotationIntrospector {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m) {return false;}
    }

    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setAnnotationIntrospector(new DisablingJsonIgnore());
        return mapper.writeValueAsBytes(object);
    }
}

