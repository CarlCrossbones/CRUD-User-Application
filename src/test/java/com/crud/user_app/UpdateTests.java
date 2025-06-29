package com.crud.user_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A test suite for /users/update
 */
@SpringBootTest
@AutoConfigureMockMvc
class UpdateTests {

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

    /**
     * A set up script
     */
    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear the repository before each test
        userRepository.save(new User("John Smith", 51, "OK"));
    }

    /**
     * A tear down script
     */
    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }

    /**
     * A test to see that updating 1 parameter works
     * @throws Exception handles exceptions
     */
    @Test
    void testUpdateUserOne() throws Exception {
        String updateJson = """
                {
                    "name": "John Smith",
                    "update": {
                        "name": "Billy Joel"
                    }
                }
                """;
        String jsonResponse = mockMvc.perform(patch("/users/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateJson))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        
        User updatedUser = new ObjectMapper().readValue(jsonResponse, User.class);
        Assertions.assertEquals("Billy Joel", updatedUser.getName());
        
    }

    /**
     * A test to see that updating 2 parameters works
     * @throws Exception handles exceptions
     */
    @Test
    void testUpdateUserTwo() throws Exception {
        String updateJson = """
                {
                    "name": "John Smith",
                    "update": {
                        "name": "Billy Joel",
                        "age": 53
                    }
                }
                """;
        String jsonResponse = mockMvc.perform(patch("/users/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateJson))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        
        User updatedUser = new ObjectMapper().readValue(jsonResponse, User.class);
        Assertions.assertEquals("Billy Joel", updatedUser.getName());
        Assertions.assertEquals(53, updatedUser.getAge());
    }

    /**
     * A test to see that updating 3 parameters works
     * @throws Exception handles exceptions
     */
    @Test
    void testUpdateUserThree() throws Exception {
        String updateJson = """
                {
                    "name": "John Smith",
                    "update": {
                        "name": "Billy Joel",
                        "age": 53,
                        "birthState": "TX"
                    }
                }
                """;
        String jsonResponse = mockMvc.perform(patch("/users/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateJson))
                                .andExpect(status().isOk())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        
        User updatedUser = new ObjectMapper().readValue(jsonResponse, User.class);
        Assertions.assertEquals("Billy Joel", updatedUser.getName());
        Assertions.assertEquals(53, updatedUser.getAge());
        Assertions.assertEquals("TX", updatedUser.getBirthState());
    }

    /**
     * A test to see that bad input is flagged
     * @throws Exception handles exceptions
     */
    @Test
    void testFlagsInvalidInput() throws Exception {
        String badInput = """
                {
                    "age": 51,
                    "update": {
                        "name": "Billy Joel",
                        "age": 53,
                        "birthState": "TX"
                    }
                }
                """;
        mockMvc.perform(patch("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(badInput))
            .andExpect(status().isBadRequest());
    }

    /**
     * A test to see that users not found is flagged
     * @throws Exception handles exceptions
     */
    @Test
    void testFlagsUserNotFound() throws Exception {
        String badInput = """
                {
                    "name": "Billy Joel",
                    "update": {
                        "name": "John Smith",
                        "age": 53,
                        "birthState": "TX"
                    }
                }
                """;
        mockMvc.perform(patch("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(badInput))
            .andExpect(status().is4xxClientError());
    }
}
