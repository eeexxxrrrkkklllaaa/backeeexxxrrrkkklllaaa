package com.prosport.place.util.field;

/**
 * @author Vlad Milyutin.
 */
public enum PlaceFields {

    PLACE_ID,
    PLACE_NAME,
    PLACE_ADDRESS,
    PLACE_STATUS,
    PLACE_INFRASTRUCTURES,
    PLACE_SPORT_TYPES;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
