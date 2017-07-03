package com.prosport.place.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Vlad Milyutin.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex){
        JsonNode responseNode = createResponseNode(ex);
        ResponseEntity response = ResponseEntity.badRequest().body(responseNode);
        return response;
    }

    private JsonNode createResponseNode(Exception ex){
        ObjectNode responseNode = new ObjectMapper().createObjectNode();
        responseNode.put("error",ex.getCause().toString());
        responseNode.put("message",ex.getMessage());
        return responseNode;
    }
}
