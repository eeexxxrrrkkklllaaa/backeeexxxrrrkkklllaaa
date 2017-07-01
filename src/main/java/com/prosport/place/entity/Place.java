package com.prosport.place.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

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

    @JsonProperty("place_status")
    @Field("place_status")
    private String status;

    @JsonProperty("place_sport_types")
    @Field("place_sport_types")
    private Set<SportType> sportTypes;

    @JsonProperty("place_infrastructures")
    @Field("place_infrastructures")
    private Set<Infrastructure> infrastructures;
}
