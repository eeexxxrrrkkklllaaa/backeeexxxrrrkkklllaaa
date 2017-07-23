package com.prosport.place.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Vlad Milyutin.
 */
@Data
public class Infrastructure {

    @JsonProperty("shower")
    private boolean shower;
    @JsonProperty("parking")
    private boolean parking;
    @JsonProperty("dressing_room")
    private boolean dressingRoom;
    @JsonProperty("tribunes")
    private boolean tribunes;
    @JsonProperty("light")
    private boolean light;
}
