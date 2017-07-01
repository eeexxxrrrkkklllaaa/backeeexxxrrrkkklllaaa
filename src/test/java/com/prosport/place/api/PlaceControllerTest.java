package com.prosport.place.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prosport.place.util.JsonUtil;
import com.prosport.place.util.constants.SportTypeConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.prosport.place.util.field.PlaceFields.*;
import static com.prosport.place.util.field.SportTypeFields.SPORT_NAME;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Vlad Milyutin.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void should_get_places() throws Exception {
        String name = "test";
        mockMvc.perform(
                get(PlaceController.ENDPOINT+name)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].place_name",is(name)));

    }

    @Test
    public void createPlace() throws Exception {
        JsonNode placeData = generatePlaceData();
        mockMvc.perform(
                post(PlaceController.ENDPOINT)
                    .content(JsonUtil.toJson(placeData))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.place_name",is(placeData.get(PLACE_NAME.toString()).asText())))
                .andExpect(jsonPath("$.place_address",is(placeData.get(PLACE_ADDRESS.toString()).asText())))
                .andExpect(jsonPath("$.place_status",is(placeData.get(PLACE_STATUS.toString()).asText())))
                .andExpect(jsonPath("$.place_sport_types[0].sport_name",
                        is(placeData.get(PLACE_SPORT_TYPES.toString()).elements().next().get(SPORT_NAME.toString()).asText())))
        ;
    }

    private ObjectNode generatePlaceData(){

        ObjectNode placeData = new ObjectMapper().createObjectNode();
        placeData.put(PLACE_NAME.toString(),"name");
        placeData.put(PLACE_ADDRESS.toString(),"address");
        placeData.put(PLACE_STATUS.toString(),"status");
        ArrayNode sportTypes = placeData.putArray(PLACE_SPORT_TYPES.toString());
        ObjectNode sportType = new ObjectMapper().createObjectNode();
        sportType.put(SPORT_NAME.toString(), SportTypeConsts.FOOTBALL.toString());
        sportTypes.add(sportType);

        return placeData;
    }

}