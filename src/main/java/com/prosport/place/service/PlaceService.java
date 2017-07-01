package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.entity.Place;

import java.util.List;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceService {
    List<Place> findAllByName(String name);
    void save(JsonNode place);
}
