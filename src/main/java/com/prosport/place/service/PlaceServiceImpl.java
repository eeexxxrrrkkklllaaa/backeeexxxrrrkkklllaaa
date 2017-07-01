package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prosport.place.entity.Place;
import com.prosport.place.repository.PlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

/**
 * @author Vlad Milyutin.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private final PlaceRepository placeRepository;
    private final ConversionService conversionService;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository,
                            ConversionService conversionService) {
        this.placeRepository = placeRepository;
        this.conversionService = conversionService;
    }


    @Override
    public JsonNode findByName(String name) {
        LOG.info("'findByName' invoked with param: '{}'", name);
        Place place = placeRepository.findPlaceByName(name);
        JsonNode placeData = conversionService.convert(place, JsonNode.class);
        LOG.info("'findByName({})' returned: '{}'", place, placeData);
        return placeData;
    }

    @Override
    public JsonNode findByAddress(String address) {
        LOG.info("'findByAddress' invoked with param: '{}'", address);
        Place place = placeRepository.findPlaceByAddress(address);
        JsonNode placeData = conversionService.convert(place, JsonNode.class);
        LOG.info("'findByAddress({})' returned: '{}'", place, placeData);
        return placeData;
    }

    @Override
    public JsonNode save(JsonNode placeData) {
        LOG.info("'save' invoked with param: '{}'", placeData);
        Place place = conversionService.convert(placeData, Place.class);
        place = placeRepository.save(place);
        JsonNode savedData = conversionService.convert(place, JsonNode.class);
        LOG.info("'save({})' returned: '{}'", place, savedData);
        return savedData;
    }

    @Override
    public JsonNode update(JsonNode placeData) {
        LOG.info("'update' invoked with param: '{}'", placeData);
        Place place = conversionService.convert(placeData, Place.class);
        placeRepository.save(place);
        LOG.info("'update({})' returned: '{}'", placeData, placeData);
        return placeData;
    }

    @Override
    public JsonNode delete(JsonNode placeData) {
        LOG.info("'delete' invoked with param: '{}'", placeData);
        Place place = conversionService.convert(placeData, Place.class);
        placeRepository.delete(place);
        ObjectNode responseData = new ObjectMapper().createObjectNode();
        responseData.put("status","deleted");
        LOG.info("'update({})' returned: '{}'", place, responseData);
        return responseData;
    }
}
