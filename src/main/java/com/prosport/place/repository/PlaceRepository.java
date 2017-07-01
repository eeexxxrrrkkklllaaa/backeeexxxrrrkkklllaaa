package com.prosport.place.repository;

import com.prosport.place.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceRepository extends MongoRepository<Place, String>{
    Place findPlaceByName(String name);
    Place findPlaceByAddress(String address);
}
