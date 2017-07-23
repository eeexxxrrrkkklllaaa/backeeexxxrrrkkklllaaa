package com.prosport.place.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosport.place.entity.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Vlad Milyutin.
 */
@Component("jsonNodeToPlaceConverter")
public class JsonNodeToPlaceConverter implements Converter<JsonNode, Place> {

    private static final Logger LOG = LoggerFactory.getLogger(JsonNodeToPlaceConverter.class);

    private ObjectMapper mapper;

    public JsonNodeToPlaceConverter(){
        mapper = new ObjectMapper();
    }

    @Override
    public Place convert(JsonNode placeData) {
        LOG.info("'convert' invoked with param: '{}'", placeData);

        Place place = null;

        try {
            place = mapper.treeToValue(placeData, Place.class);
        } catch (JsonProcessingException e) {
            LOG.error("Can't convert to place object");
            LOG.error(e.getMessage() + e.getStackTrace());
        }

        LOG.info("'convert({})' returned: '{}'", placeData, place);
        return place;
    }

    @Component("placeToJsonNodeConverter")
    public static class PlaceToJsonNodeConverter implements Converter<Place, JsonNode>{

        private ObjectMapper mapper;

        @Override
        public JsonNode convert(Place place) {
            LOG.info("'convert' invoked with param: '{}'", place);

            mapper = new ObjectMapper();

            JsonNode placeData  = mapper.valueToTree(place);

            LOG.info("'convert({})' returned: '{}'", place, placeData);
            return placeData;
        }
    }
}
