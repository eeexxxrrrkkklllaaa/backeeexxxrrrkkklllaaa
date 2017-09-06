package com.prosport.place.entity.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Vlad Milyutin.
 */
@Data
public class Sport {

    @JsonProperty("football")
    private boolean football;
    @JsonProperty("volleyball")
    private boolean volleyball;
    @JsonProperty("hokey")
    private boolean hokey;
    @JsonProperty("swimming")
    private boolean swimming;

}
