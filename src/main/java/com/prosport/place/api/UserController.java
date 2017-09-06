package com.prosport.place.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.prosport.place.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.prosport.place.api.UserController.ENDPOINT;

/**
 * @author Vlad Milyutin.
 */
@RestController
@Slf4j
@RequestMapping(value = ENDPOINT)
public class UserController {

    public static final String ENDPOINT = "/api/v1/user/";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody JsonNode userData){
        log.info("'saveUser' invoked with param: '{}'", userData);

        userService.saveUser(userData);

        ResponseEntity response = new ResponseEntity(HttpStatus.CREATED);
        log.info("'saveUser' returned: '{}'", response);
        return response;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody JsonNode userData, Principal principal){
        log.info("'updateUser' invoked with param: '{}'", userData);

        JsonNode response = userService.updateUser(userData, principal.getName());

        log.info("'updateUser' returned: '{}'", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(Principal principal){
        log.info("'getUser' invoked");

        JsonNode response = userService.findUserByUserName(principal.getName());

        log.info("'getUser' returned: '{}'");
        return ResponseEntity.ok(response);
    }
}
