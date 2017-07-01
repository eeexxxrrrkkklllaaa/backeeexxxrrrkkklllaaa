package com.prosport.place.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.entity.Place;
import com.prosport.place.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vlad Milyutin.
 */
@RestController
@RequestMapping(PlaceController.ENDPOINT)
public class PlaceController {

    public static final String ENDPOINT = "/api/v1/places/";

    @Autowired
    private PlaceService placeService;

    @GetMapping(value = "/{name}")
    public List<Place> getPlacesByName(@PathVariable(value = "name") String name){
        return placeService.findAllByName(name);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPlace(@RequestBody JsonNode place){
        placeService.save(place);
        ResponseEntity response = ResponseEntity.ok(place);
        return response;
    }

}
