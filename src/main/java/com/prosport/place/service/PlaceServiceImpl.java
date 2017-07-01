package com.prosport.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.entity.Place;
import com.prosport.place.repository.PlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Place> findAllByName(String name) {
        LOG.info("'finAllByName' invoked with param: '{}'", name);

        List<Place> places = placeRepository.findAllByName(name);

        LOG.info("'findAllByName({})' returned: '{}'", name, places);
        return places;
    }

    @Override
    public void save(JsonNode placeData) {
        LOG.info("'save' invoked with param: '{}'", placeData);
        Place place = conversionService.convert(placeData, Place.class);
        placeRepository.save(place);
        LOG.info("Successfully saved: '{}'", place);
    }
}
