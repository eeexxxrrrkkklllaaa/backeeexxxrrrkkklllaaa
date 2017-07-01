package com.prosport.place.repository;

import com.prosport.place.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceRepository extends MongoRepository<Place, String>{
    List<Place> findAllByName(String name);
}
