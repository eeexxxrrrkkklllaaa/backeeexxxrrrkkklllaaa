package com.prosport.place.repository;

import com.prosport.place.entity.place.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

/**
 * @author Vlad Milyutin.
 */
public interface PlaceRepository extends MongoRepository<Place, String>{

    Place findByName(String name);
    Place findByAddress(String address);
    Set<Place> findByStatus(String status);
    @Query("{place_infrastructures:?0}")
    Set<Place> findByInfrastructureName(String infrastructureName);
    @Query("{place_sport_types:?0}")
    Set<Place> findBySportTypeName(String sportTypeName);
    /**
     *  Find Places in specific square with specific coordinates
     *  x1, x2 - left and right latitude boundaries
     *  y1, y2 - top and bottom longitude boundaries.
     */
    @Query("{lat:{$gte:?0, $lte:?1}, lon:{$gte:?2, $lte:?3}}")
    Set<Place> findInSpecificSquare(double x1, double x2, double y1, double y2);
}
