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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class DeleteTests {

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
    // A test to see that you can successfully delete a user by name
    void testDeleteUserByName() throws Exception {
        String json = """
                {
                    "name": "John Smith"
                }
                """;
        mockMvc.perform(delete("/users/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
        Assertions.assertEquals(0, userRepository.findAll().size());
    }

    @Test
    // A test to see that you can successfully delete a user by id
    void testDeleteUserById() throws Exception {
        String jsonResponse1 = mockMvc.perform(get("/users").param("name", "John Smith"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse1, User[].class);
        User user = users[0];
        Long userId = user.getId();

        String json = "{\"id\": "+userId+"}";
        mockMvc.perform(delete("/users/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
        Assertions.assertEquals(0, userRepository.findAll().size());
    }

    @Test
    // A test to see that bad input is flagged
    void testFlagsBadInput() throws Exception {
        String json = """
                {
                    "age": 51
                }
                """;
        mockMvc.perform(delete("/users/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    @Test
    // A test to see that user not found error is flagged
    void testUserNotFound() throws Exception {
        String json = """
                {
                    "name": "Sarah Smith"
                }
                """;
        mockMvc.perform(delete("/users/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().is4xxClientError());
        Assertions.assertEquals(1, userRepository.findAll().size());
    }
}