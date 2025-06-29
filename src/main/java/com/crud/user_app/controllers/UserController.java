package com.crud.user_app.controllers;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserDelete;
import com.crud.user_app.persistent.UserRepository;
import com.crud.user_app.persistent.UserUpdate;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
    public User addUser(@Valid @RequestBody User user) {
        // Prevent duplicate entries along the "name" parameter.
        if (!userRepository.findByName(user.getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User with name \""+user.getName()+"\" already exists");
        }

        userRepository.save(user);
        return user;
    }

    /**
     * UPDATE: An endpoint to update existing users in the database
     * @param userUpdate defined in UserUpdate.java
     * @return the updated user
     */
    @PatchMapping("/update")
    @Transactional
    public User updateUser(@Valid @RequestBody UserUpdate userUpdate) {
        Optional<User> optionalUser;

        // If blocks for Id vs. Name field entries
        if (userUpdate.getId() != null) {
            // If user exists in database, else throw error
            if (userRepository.existsById(userUpdate.getId())) {
                optionalUser = userRepository.findById(userUpdate.getId());
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found in database");
            }
        } else if (userUpdate.getName() != null) {
            // If user exists in database, else throw error
            if (userRepository.findByName(userUpdate.getName()) != null) {
                optionalUser = userRepository.findByName(userUpdate.getName()).stream().findFirst();
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        // Convert Optional to User
        User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        UserUpdate.UpdateFields update = userUpdate.getUpdate();
        
        // If name update is present, update name
        if (update.getName() != null) {
            user.setName(update.getName());
        }

        // If age update is present, update age
        if (update.getAge() != null) {
            user.setAge(update.getAge());
        }

        // If birth state update is present, update birth state
        if (update.getBirthState() != null) {
            user.setBirthState(update.getBirthState());
        }

        //Update user's information in the database
        userRepository.save(user);

        return user;
    }

    @DeleteMapping("/delete")
    @Transactional
    public User deleteUser (@Valid @RequestBody UserDelete userDelete) {
        Optional<User> optionalUser;

        // If blocks for Id vs. Name field entries
        if (userDelete.getId() != null) {
            // If user exists in database, else throw error
            if (userRepository.existsById(userDelete.getId())) {
                optionalUser = userRepository.findById(userDelete.getId());
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found in database");
            }
        } else if (userDelete.getName() != null) {
            // If user exists in database, else throw error
            if (userRepository.findByName(userDelete.getName()) != null) {
                optionalUser = userRepository.findByName(userDelete.getName()).stream().findFirst();
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }

        // Convert Optional to User
        User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        userRepository.delete(user);

        return user;
    }
}
