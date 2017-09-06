package com.prosport.place.entity.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Vlad Milyutin.
 */
@Data
@Document(collection = "places")
public class Place {

    @Id
    @JsonProperty("place_id")
    private String id;

    @JsonProperty("place_name")
    @Field("place_name")
    private String name;

    @JsonProperty("place_address")
    @Field("place_address")
    private String address;

    @JsonProperty("lat")
    @Field("lat")
    private double lat;

    @JsonProperty("lon")
    @Field("lon")
    private double lon;

    @JsonProperty("place_status")
    @Field("place_status")
    private String status;

    @JsonProperty("place_sport_types")
    @Field("place_sport_types")
    private Sport sportTypes;

    @JsonProperty("place_infrastructures")
    @Field("place_infrastructures")
    private Infrastructure infrastructure;

}
