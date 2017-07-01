package com.prosport.place.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vlad Milyutin.
 */
@Data
@Document
public class Infrastructure {

    @JsonProperty("infrastructure_name")
    private String name;
}
