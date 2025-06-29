package com.crud.user_app.controllers;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String CHANGEME() {
        return "Hello World";
    }
    
}
