package com.crud.user_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import com.crud.user_app.persistent.User;
import com.crud.user_app.persistent.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ReadTests {

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

        userRepository.save(new User("John Smith", 51, "OK"));
        userRepository.save(new User("John Doe", 51, "OK"));
        userRepository.save(new User("Jane Smith", 30, "OK"));
        userRepository.save(new User("Jane Doe", 30, "TX"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }
	@Test
	// A test to see that hitting /users returns a list of all users
	void testReturnsAllUsers() throws Exception {
		String responseJson = mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        
        User[] users = new ObjectMapper().readValue(responseJson, User[].class);
        Assertions.assertEquals(4, users.length);
        Assertions.assertEquals("John Smith", users[0].getName());
        Assertions.assertEquals("Jane Doe", users[3].getName());
	}

    @Test
	// A test to see that hitting /users with a query parameter id returns a particular user
	void testReturnsWithId() throws Exception {

        String jsonResponse1 = mockMvc.perform(get("/users").param("name", "John Smith"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse1, User[].class);
        User user = users[0];
        Long userId = user.getId();

		String jsonResponse = mockMvc.perform(get("/users").param("id", String.valueOf(userId)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users1 = new ObjectMapper().readValue(jsonResponse, User[].class);
        User user1 = users[0];
        Assertions.assertEquals(1, users1.length);
        Assertions.assertEquals("John Smith", user1.getName());
	}

    @Test
	// A test to see that hitting /user with a query parameter name returns a particular user 
    // (the repository should only allow for one entry per name)
	void testReturnsWithName() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users").param("name", "John Smith"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse, User[].class);
        User user = users[0];
        Assertions.assertEquals(1, users.length);
        Assertions.assertEquals("John Smith", user.getName());
	}

    @Test
	// A test to see that hitting /user with a query parameter age returns all users of that age
	void testReturnsWithAge() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users").param("age", "51"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse, User[].class);
        Assertions.assertEquals(2, users.length);
        Assertions.assertEquals("John Smith", users[0].getName());
        Assertions.assertEquals("John Doe", users[1].getName());
	}

    @Test
	// A test to see that hitting /user with a query parameter birth state returns all users of that birth state
	void testReturnsWithState() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users").param("birthState", "TX"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse, User[].class);
        Assertions.assertEquals(1, users.length);
        Assertions.assertEquals("Jane Doe", users[0].getName());
	}

    @Test
	// A test to see that bad input returns 400
	void testFlagsInvalidInput() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users").param("foo", "bar"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse, User[].class);
        Assertions.assertEquals(4, users.length);;
	}

    @Test
	// A test to see that no user found with query parameters returns empty.
	void testNoUserFound() throws Exception {
		String jsonResponse = mockMvc.perform(get("/users").param("age", "21"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        User[] users = new ObjectMapper().readValue(jsonResponse, User[].class);
        Assertions.assertEquals(0, users.length);
	}
}
