package com.prosport.place.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.entity.system.User;
import com.prosport.place.repository.UserRepository;
import com.prosport.place.service.UserService;
import com.prosport.place.util.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Vlad Milyutin.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public JsonNode saveUser(JsonNode userData) {
        log.info("'saveUser' invoked with param: '{}'", userData);

        User user = Converter.convertToObject(userData, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        userData = Converter.convertToNode(user);

        log.info("'saveUser' returned: '{}'", userData);
        return userData;
    }

    @Override
    public JsonNode updateUser(JsonNode userData, String username) {
        log.info("'updateUser' invoked with param: '{}'", userData);

        User fetchedUser = userRepository.findByUsername(username);
        User user = Converter.convertToObject(userData, User.class);
        user = copyNonNullFields(user, fetchedUser);

        userData = Converter.convertToNode(user);

        log.info("'updateUser' returned: '{}'", userData);
        return userData;
    }

    @Override
    public void delete(String username) {
        log.info("'deleteUser' invoked with param: '{}'", username);

        User fetchedUser = userRepository.findByUsername(username);
        userRepository.delete(fetchedUser);
    }

    @Override
    public JsonNode findUserByUserName(String username) {
        log.info("'findUserByUserName' invoked with param: '{}'", username);

        User user = userRepository.findByUsername(username);
        JsonNode userData = Converter.convertToNode(user);

        log.info("'findUserByUserName' returned: '{}'", userData);
        return userData;
    }

    private User copyNonNullFields(User updatedUser, User oldUser){
        log.info("'copyNonNullFields' invoked with params: '{}', '{}'", updatedUser, oldUser);

        if(updatedUser.getPassword() != null){
            oldUser.setPassword(updatedUser.getPassword());
        }

        if(updatedUser.getUsername() != null){
            oldUser.setUsername(updatedUser.getPassword());
        }

        if(updatedUser.getRoles() != null){
            oldUser.setRoles(updatedUser.getRoles());
        }

        log.info("'copyNonNullFields' returned: '{}'", oldUser);
        return oldUser;
    }
}
