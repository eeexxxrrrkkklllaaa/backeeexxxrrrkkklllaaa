package com.prosport.place.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Vlad Milyutin.
 */
@Data
@Document(collection = "sport_types")
public class SportType {

    @JsonProperty("sport_name")
    @Field("sport_name")
    private String name;

}
