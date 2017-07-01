package com.prosport.place.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vlad Milyutin.
 */
@RestController
@RequestMapping(PlaceController.ENDPOINT)
public class PlaceController {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

    public static final String ENDPOINT = "/api/v1/places/";

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity getPlaceByName(@PathVariable(value = "name") String name){
        LOG.info("'getPlaceByName' invoked with param: '{}'", name);
        ResponseEntity response = ResponseEntity.ok(placeService.findByName(name));
        LOG.info("'getPlaceByName({})' returned: '{}'", name, response);
        return response;
    }

    @GetMapping(value = "/address/{address}")
    public ResponseEntity getPlaceByAddress(@PathVariable(value = "address") String address){
        LOG.info("'getPlaceByAddress' invoked with param: '{}'", address);

        ResponseEntity response = ResponseEntity.ok(placeService.findByAddress(address));

        LOG.info("'getPlaceByAddress({})' returned: '{}'", address, response);
        return response;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPlace(@RequestBody JsonNode placeData){
        LOG.info("'createPlace' invoked with param: '{}'", placeData);
        ResponseEntity response = ResponseEntity.ok(placeService.save(placeData));
        LOG.info("'createPlace({})' returned: '{}'", placeData, response);
        return response;
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePlace(@RequestBody JsonNode placeData){
        LOG.info("'createPlace' invoked with param: '{}'", placeData);
        ResponseEntity response = ResponseEntity.ok(placeService.update(placeData));
        LOG.info("'createPlace({})' returned: '{}'", placeData, response);
        return response;
    }

    @DeleteMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePlace(@RequestBody JsonNode placeData){
        LOG.info("'createPlace' invoked with param: '{}'", placeData);
        ResponseEntity response = ResponseEntity.ok(placeService.delete(placeData));
        LOG.info("'createPlace({})' returned: '{}'", placeData, response);
        return response;
    }




}
