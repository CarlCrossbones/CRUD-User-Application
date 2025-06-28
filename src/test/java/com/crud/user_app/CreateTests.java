package com.crud.user_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.crud.user_app.persistence.User;
import com.crud.user_app.persistence.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
class CreateTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String validInput = """
            {
            "name": "John Smith",
            "age": 51,
            "birthState": "OK"
            }
    """;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear the repository before each test
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }

    @Test
    // A test to see that hitting /users/create successfully adds a user
    void testCreateUser() throws Exception {
        User user = new User("John Smith", 51, "OK");
        createUser(validInput);
        User savedUser = userRepository.findByName("John Smith");

        Assertions.assertEquals("John Smith", savedUser.getName());
        Assertions.assertEquals(51, savedUser.getAge());
        Assertions.assertEquals("OK", savedUser.getBirthState());
    }

    @Test
    // A test to see that hitting /users/create successfully blocks an invalid key or value
    void testFlagsInvalidInput() throws Exception{
        
        String invalidInput = """
                {
                "id": "John Smith",
                "age": -1,
                "birthState": "OK"
                }
        """;

        createUser(invalidInput)
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(userRepository.findAll().isEmpty(), "Repository should be empty after invalid input");

        String invalidInput2 = """
                {
                "name": 1,
                "age": 51,
                "birthState": ""
                }
        """;

        createUser(invalidInput2)
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(userRepository.findAll().isEmpty(), "Repository should be empty after invalid input");
    }

    @Test
    // A test to see that hitting /users/create successfully blocks a duplicate entry
    void testBlocksDuplicateEntry() throws Exception{
        createUser(validInput)
                .andExpect(status().isOk());
        
        createUser(validInput)
                .andExpect(status().is4xxClientError());
        Assertions.assertEquals(1, userRepository.count(), "Repository should contain only one user after duplicate entry attempt");
    }

    private ResultActions createUser(String input) throws Exception {
        return mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
    }
}