package com.crud.user_app.controllers;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;

import jakarta.transaction.Transactional;

import com.crud.user_app.controllers.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * The Controller class that wires all endpoints in the service.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * READ: An endpoint to return all users, or a specific set of users.
     * @return a set of user's meeting the parameter specification.
     */
    @GetMapping("")
    public List<User> getUsers(
            // User's name
            @RequestParam(required = false) String name,
            // User's age
            @RequestParam(required = false) Integer age,
            // User's birth state
            @RequestParam(required = false) String birthState,
            // User's id
            @RequestParam(required = false) Long id) {
        
        Specification<User> spec = Specification.where(UserSpecification.hasName(name))
                                                    .and(UserSpecification.hasAge(age))
                                                    .and(UserSpecification.hasBirthState(birthState))
                                                    .and(UserSpecification.hasId(id));
        
        return userRepository.findAll(spec);
    }


    /**
     * CREATE: An endpoint to facilitate the addition of new user's to the database
     * @param name The name of the user
     * @param age The age of the user
     * @param birthState The birth state of the user
     * @return A new user object
     */
    @PostMapping("/create")
    @Transactional
    public User addUser(String name, Integer age, String birthState) {
        // Prevent duplicate entries along the "name" parameter.
        if (!userRepository.findByName(name).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User with name \""+name+"\" already exists");
        }

        User newUser = new User(name, age, birthState);

        userRepository.save(newUser);
        return newUser;
    }
}
