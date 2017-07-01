package com.prosport.place.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prosport.place.entity.Place;
import com.prosport.place.repository.PlaceRepository;
import com.prosport.place.service.PlaceService;
import com.prosport.place.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.prosport.place.util.constants.InfrastructuresConsts.SHOWER;
import static com.prosport.place.util.constants.SportTypeConsts.FOOTBALL;
import static com.prosport.place.util.field.InfrastuctureFields.INFRASTRUCTURE_NAME;
import static com.prosport.place.util.field.PlaceFields.*;
import static com.prosport.place.util.field.SportTypeFields.SPORT_NAME;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Vlad Milyutin.
 */
//TODO fix rollback
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceService placeService;

    @Before
    public void insert_tested_data(){
        Place place = new Place();
        place.setName("test");
        place.setAddress("test");
        place.setStatus("test");
        placeRepository.save(place);
    }

    @Test
    public void should_get_place_by_name() throws Exception {
        String name = "test";
        mockMvc.perform(
                get(PlaceController.ENDPOINT+"/name/"+name)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.place_name",is(name)));

    }

    @Test
    public void should_get_place_by_address() throws Exception {
        String address = "test";
        mockMvc.perform(
                get(PlaceController.ENDPOINT+"/address/"+address)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.place_address",is(address)));

    }

    @Test
    public void should_create_place() throws Exception {
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
                .andExpect(jsonPath("$.place_infrastructures[0].infrastructure_name",
                        is(placeData.get(PLACE_INFRASTRUCTURES.toString()).elements().next().get(INFRASTRUCTURE_NAME.toString()).asText())))
        ;
    }

    @Test
    public void should_update_place() throws Exception {
        JsonNode placeData = generatePlaceData();
        JsonNode saved = placeService.save(placeData);
        ((ObjectNode) saved).put(PLACE_NAME.toString(),"updated");
        mockMvc.perform(
                put(PlaceController.ENDPOINT)
                        .content(JsonUtil.toJson(saved))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.place_name",is(saved.get(PLACE_NAME.toString()).asText())))
                .andExpect(jsonPath("$.place_address",is(saved.get(PLACE_ADDRESS.toString()).asText())))
                .andExpect(jsonPath("$.place_status",is(saved.get(PLACE_STATUS.toString()).asText())))
                .andExpect(jsonPath("$.place_sport_types[0].sport_name",
                        is(saved.get(PLACE_SPORT_TYPES.toString()).elements().next().get(SPORT_NAME.toString()).asText())))
                .andExpect(jsonPath("$.place_infrastructures[0].infrastructure_name",
                        is(saved.get(PLACE_INFRASTRUCTURES.toString()).elements().next().get(INFRASTRUCTURE_NAME.toString()).asText())))
        ;

        JsonNode updated = placeService.findByName(saved.get(PLACE_NAME.toString()).asText());

        assertNotNull(updated);
        assertEquals(saved, updated);
    }

    @Test
    public void should_delete_place() throws Exception {
        JsonNode placeData = generatePlaceData();
        placeData = placeService.save(placeData);
        mockMvc.perform(
                delete(PlaceController.ENDPOINT)
                        .content(JsonUtil.toJson(placeData))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status",is("deleted")))
        ;
    }



    private ObjectNode generatePlaceData(){

        ObjectNode placeData = new ObjectMapper().createObjectNode();
        placeData.put(PLACE_NAME.toString(),"name");
        placeData.put(PLACE_ADDRESS.toString(),"address");
        placeData.put(PLACE_STATUS.toString(),"status");
        ArrayNode sportTypes = placeData.putArray(PLACE_SPORT_TYPES.toString());
        ObjectNode sportType = new ObjectMapper().createObjectNode();
        sportType.put(SPORT_NAME.toString(), FOOTBALL.toString());
        sportTypes.add(sportType);
        ArrayNode infrastructures = placeData.putArray(PLACE_INFRASTRUCTURES.toString());
        ObjectNode infrastructure = new ObjectMapper().createObjectNode();
        infrastructure.put(INFRASTRUCTURE_NAME.toString(), SHOWER.toString());
        infrastructures.add(infrastructure);

        return placeData;
    }

}