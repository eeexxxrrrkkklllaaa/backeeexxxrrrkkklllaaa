package com.prosport.place.repository;

import com.prosport.place.entity.system.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Milyutin.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{

    User findByUsername(String username);
    void deleteByUsername(String username);

}
