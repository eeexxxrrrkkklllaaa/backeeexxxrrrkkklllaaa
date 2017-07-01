package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceService {
    JsonNode findByName(String name);
    JsonNode findByAddress(String address);
    JsonNode save(JsonNode placeData);
    JsonNode update(JsonNode placeData);
    JsonNode delete(JsonNode placeData);
}
