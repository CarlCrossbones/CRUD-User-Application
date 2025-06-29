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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class UpdateTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clear the repository before each test
        userRepository.save(new User("John Smith", 51, "OK"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }

    @Test
    // A test to see that updating 1 parameter works
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

    @Test
    // A test to see that updating 2 parameters works
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

    @Test
    // A test to see that updating 3 parameters works
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

    @Test
    // A test to see that bad input is flagged
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

    @Test
    // A test to see that users not found is flagged
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
