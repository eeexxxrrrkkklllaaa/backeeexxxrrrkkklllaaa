package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.util.JSON;

/**
 * @author Vlad Milyutin.
 */
public interface UserService {

    JsonNode saveUser(JsonNode user);
    JsonNode updateUser(JsonNode user, String username);
    void delete(String username);
    JsonNode findUserByUserName(String username);
}
