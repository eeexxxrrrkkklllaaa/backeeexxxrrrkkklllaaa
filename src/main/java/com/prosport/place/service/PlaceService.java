package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Set;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceService {
    JsonNode findByName(String name);
    JsonNode findByAddress(String address);
    JsonNode save(JsonNode placeData);
    JsonNode update(JsonNode placeData);
    JsonNode delete(JsonNode placeData);
    Set<JsonNode> findBySportTypeName(String sportTypeName);
    Set<JsonNode> findByInfrastructureName(String infrastructureName);
    Set<JsonNode> findInSpecificSquare(double lat, double lon, long radius);
}
