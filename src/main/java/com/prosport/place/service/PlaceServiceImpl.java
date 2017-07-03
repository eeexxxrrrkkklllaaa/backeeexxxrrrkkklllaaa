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

import java.util.HashSet;
import java.util.Set;

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

        Place place = placeRepository.findByName(name);
        JsonNode placeData = conversionService.convert(place, JsonNode.class);

        LOG.info("'findByName({})' returned: '{}'", place, placeData);
        return placeData;
    }

    @Override
    public JsonNode findByAddress(String address) {
        LOG.info("'findByAddress' invoked with param: '{}'", address);

        Place place = placeRepository.findByAddress(address);
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

    @Override
    public Set<JsonNode> findByInfrastructureName(String infrastructureName) {
        LOG.info("'findByInfrastructureName' invoked with param: '{}'", infrastructureName);

        Set<Place> placeSet = placeRepository.findByInfrastructureName(infrastructureName);
        Set<JsonNode> placeDataSet = new HashSet<>();
        placeSet.forEach(place -> placeDataSet.add(conversionService.convert(place, JsonNode.class)));

        LOG.info("'findByInfrastructureName({})' returned: '{}'", infrastructureName, placeDataSet);
        return placeDataSet;
    }

    @Override
    public Set<JsonNode> findBySportTypeName(String sportTypeName) {
        LOG.info("'findBySportTypeName' invoked with param: '{}'", sportTypeName);

        Set<Place> placeSet = placeRepository.findBySportTypeName(sportTypeName);
        Set<JsonNode> placeDataSet = new HashSet<>();
        placeSet.forEach(place -> placeDataSet.add(conversionService.convert(place, JsonNode.class)));

        LOG.info("'findBySportTypeName({})' returned: '{}'", sportTypeName, placeDataSet);
        return placeDataSet;
    }

    @Override
    public Set<JsonNode> findInSpecificSquare(double lat, double lon, long radius) {
        LOG.info("'findInSpecificSquare' invoked with params: '{}', '{}', '{}'", lat, lon, radius);

        Set<Place> placeSet =
                placeRepository.findInSpecificSquare(lat - radius, lat + radius, lon - radius, lon + radius);
        Set<JsonNode> placeDataSet = new HashSet<>();
        placeSet.forEach(place -> placeDataSet.add(conversionService.convert(place, JsonNode.class)));

        LOG.info("'findInSpecificSquare({}, {}, {})' returned: ", lat, lon, radius, placeDataSet);
        return placeDataSet;
    }
}
