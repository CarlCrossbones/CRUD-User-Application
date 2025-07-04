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

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;

import java.util.List;

/**
 * A test suite for testing /users/create
 */
@SpringBootTest
@AutoConfigureMockMvc
class CreateTests {

    /**
     * The database interaction object
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Provides the tests with the ability to hit endpoints
     */
    @Autowired
    private MockMvc mockMvc;

    private static final String validInput = """
            {
            "name": "John Smith",
            "age": 51,
            "birthState": "OK"
            }
    """;

    /**
     * A set up script
     */
    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear the repository before each test
    }

    /**
     * A teardown script
     */
    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }

    /**
     * A test to see that hitting /users/create successfully adds a user
     * @throws Exception handles exceptions
     */
    @Test
    void testCreateUser() throws Exception {
        User user = new User("John Smith", 51, "OK");
        createUser(validInput);
        List<User> savedUsers = userRepository.findAll();

        Assertions.assertEquals("John Smith", savedUsers.get(0).getName());
        Assertions.assertEquals(51, savedUsers.get(0).getAge());
        Assertions.assertEquals("OK", savedUsers.get(0).getBirthState());
    }

    /**
     * A test to see that hitting /users/create successfully blocks an invalid key or value
     * @throws Exception handles exceptions
     */
    @Test
    void testFlagsInvalidInput() throws Exception{
        
        String invalidInput = """
                {
                "id": "John Smith",
                "age": "twelve",
                "birthState": "OK"
                }
        """;

        createUser(invalidInput)
                .andExpect(status().isBadRequest());
        Assertions.assertTrue(userRepository.findAll().isEmpty(), "Repository should be empty after invalid input");
    }

    /**
     * A test to see that hitting /users/create successfully blocks a duplicate entry
     * @throws Exception handles exceptions
     */
    @Test
    void testBlocksDuplicateEntry() throws Exception{
        createUser(validInput)
                .andExpect(status().isOk());
        
        createUser(validInput)
                .andExpect(status().is4xxClientError());
        Assertions.assertEquals(1, userRepository.count(), "Repository should contain only one user after duplicate entry attempt");
    }

    /**
     * A class to handle the hitting of /users/create
     * @param input the json payload
     * @return A ResultActions object
     * @throws Exception handles exception
     */
    private ResultActions createUser(String input) throws Exception {
        return mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));
    }
}