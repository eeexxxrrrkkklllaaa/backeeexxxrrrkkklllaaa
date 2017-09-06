package com.prosport.place.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prosport.place.entity.place.Place;
import com.prosport.place.repository.PlaceRepository;
import com.prosport.place.service.PlaceService;
import com.prosport.place.sorter.PlaceSorter;
import com.prosport.place.util.converter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Vlad Milyutin.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private final PlaceRepository placeRepository;
    private final PlaceSorter placeSorter;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository,
                            PlaceSorter placeSorter) {
        this.placeRepository = placeRepository;
        this.placeSorter = placeSorter;
    }

    @Override
    public Collection<JsonNode> findAll() {
        LOG.info("'findAll' invoked");

        List<Place> places = placeRepository.findAll();
        Set<JsonNode> placeSet = new HashSet<>();
        places.forEach(place -> placeSet.add(Converter.convertToNode(place)));

        LOG.info("'findAll' returned: '{}'", placeSet);
        return placeSet;
    }

    @Override
    public JsonNode findByName(String name) {
        LOG.info("'findByName' invoked with param: '{}'", name);

        Place place = placeRepository.findByName(name);
        JsonNode placeData = Converter.convertToNode(place);

        LOG.info("'findByName({})' returned: '{}'", place, placeData);
        return placeData;
    }

    @Override
    public JsonNode findByAddress(String address) {
        LOG.info("'findByAddress' invoked with param: '{}'", address);

        Place place = placeRepository.findByAddress(address);
        JsonNode placeData = Converter.convertToNode(place);

        LOG.info("'findByAddress({})' returned: '{}'", place, placeData);
        return placeData;
    }

    @Override
    public JsonNode save(JsonNode placeData) {
        LOG.info("'save' invoked with param: '{}'", placeData);

        Place place = Converter.convertToObject(placeData, Place.class);
        place = placeRepository.save(place);
        JsonNode savedData = Converter.convertToNode(place);

        LOG.info("'save({})' returned: '{}'", place, savedData);
        return savedData;
    }

    @Override
    public JsonNode update(JsonNode placeData) {
        LOG.info("'update' invoked with param: '{}'", placeData);

        Place place = Converter.convertToObject(placeData, Place.class);
        placeRepository.save(place);

        LOG.info("'update({})' returned: '{}'", placeData, placeData);
        return placeData;
    }

    @Override
    public JsonNode delete(JsonNode placeData) {
        LOG.info("'delete' invoked with param: '{}'", placeData);

        Place place = Converter.convertToObject(placeData, Place.class);
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
        placeSet.forEach(place -> placeDataSet.add(Converter.convertToNode(place)));

        LOG.info("'findByInfrastructureName({})' returned: '{}'", infrastructureName, placeDataSet);
        return placeDataSet;
    }

    @Override
    public Set<JsonNode> findBySportTypeName(String sportTypeName) {
        LOG.info("'findBySportTypeName' invoked with param: '{}'", sportTypeName);

        Set<Place> placeSet = placeRepository.findBySportTypeName(sportTypeName);
        Set<JsonNode> placeDataSet = new HashSet<>();
        placeSet.forEach(place -> placeDataSet.add(Converter.convertToNode(place)));

        LOG.info("'findBySportTypeName({})' returned: '{}'", sportTypeName, placeDataSet);
        return placeDataSet;
    }

    @Override
    public Set<JsonNode> findInSpecificSquare(double lat, double lon, long radius) {
        LOG.info("'findInSpecificSquare' invoked with params: '{}', '{}', '{}'", lat, lon, radius);

        Set<Place> placeSet =
                placeRepository.findInSpecificSquare(lat - radius, lat + radius, lon - radius, lon + radius);
        Set<JsonNode> placeDataSet = new HashSet<>();
        placeSet.forEach(place -> placeDataSet.add(Converter.convertToNode(place)));

        LOG.info("'findInSpecificSquare({}, {}, {})' returned: ", lat, lon, radius, placeDataSet);
        return placeDataSet;
    }

    /**
     * Sorting Collection Places by SportTypes and Infrastructures
     * @param placeData - Collection of Places
     * @param sportParam - String of sport params separated by coma
     * @param infParam  - String of infrastructure params separated by coma
     * @return sorted Collection<JsonNode>
     */
    @Override
    public Collection<JsonNode> sort(Collection<JsonNode> placeData, String sportParam, String infParam) throws Exception {
        LOG.info("'sort' invoked with params: '{}', '{}', '{}'");

        // Converting all JsonNode to Place
        Collection<JsonNode> sortedPlacesNodes = new ArrayList<>();
        List<Place> placeList = new ArrayList<>();
        List<Place> sortedList = new ArrayList<>();
        placeData.forEach(place -> placeList.add(Converter.convertToObject(place, Place.class)));

        //Checking that sportParam is not empty, and if not do sorting by sportParams
        if(!sportParam.contains("none")){
            LOG.info("Sport Params not empty");
            sortedList = (List) placeSorter.sortBySportType(placeList, sportParam);
        }
        else {
            sortedList = placeList;
        }
        //Checking that infParam is not empty
        if(!infParam.contains("none")){
            LOG.info("Inf Params not empty");
            sortedList = (List) placeSorter.sortByInfrastructure(sortedList, infParam);
        }

        // Converting back to JsonNode
        sortedList.forEach(place -> sortedPlacesNodes.add(Converter.convertToNode(place)));

        LOG.info("'sort({},{},{})' returned: '{}'", placeData, sportParam, infParam, sortedPlacesNodes);
        return sortedPlacesNodes;
    }
}
