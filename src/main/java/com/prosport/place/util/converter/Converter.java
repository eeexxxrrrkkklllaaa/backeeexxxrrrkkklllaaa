package com.prosport.place.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Vlad Milyutin.
 */
@Slf4j
public class Converter {

    public static JsonNode convertToNode(Object data){
        log.info("'convertToObject' invoked with param: '{}'", data);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode convertedData = mapper.valueToTree(data);

        log.info("'convertToObject' returned: '{}'", convertedData);
        return convertedData;
    }

    public static <T> T convertToObject(JsonNode data, Class<T> clazz) {
        log.info("'convertToObject' invoked with params: '{}','{}'", data, clazz);
        Object o = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            o = mapper.treeToValue(data, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert to {}", clazz);
            e.printStackTrace();
        }
        try {
            log.info("'convertToObject' returned: '{}'", clazz.cast(o));
            return clazz.cast(o);
        } catch(ClassCastException e) {
            return null;
        }
    }
}
