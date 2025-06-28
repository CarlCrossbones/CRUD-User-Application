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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.crud.user_app.persistence.User;
import com.crud.user_app.persistence.UserRepository;

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

        userRepostiory.save(new User("John Smith", 51, "OK"));
        userRepository.save(new User("John Doe", 51, "OK"));
        userRepository.save(new User("Jane Smith", 30, "OK"));
        userRepostiory.save(new User("Jane Doe", 30, "TX"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Clear the repository after each test
    }
	@Test
    // TODO
	// A test to see that hitting /users returns a list of all users
	void testReturnsAllUsers() {
		mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andReturn();
	}

    @Test
    // TODO
	// A test to see that hitting /users with a query parameter id returns a particular user
	void testReturnsWithId() {
		Assertions.assertTrue(true);
	}

    @Test
    // TODO
	// A test to see that hitting /user with a query parameter name returns a particular user 
    // (the repository should only allow for one entry per name)
	void testReturnsWithName() {
		Assertions.assertTrue(true);
	}

    @Test
    // TODO
	// A test to see that hitting /user with a query parameter age returns all users of that age
	void testReturnsWithAge() {
		Assertions.assertTrue(true);
	}

    @Test
    // TODO
	// A test to see that hitting /user with a query parameter birth state returns all users of that birth state
	void testReturnsWithState() {
		Assertions.assertTrue(true);
	}

    @Test
    // TODO
	// A test to see that bad input returns a 400
	void testFlagsInvalidInput() {
		Assertions.assertTrue(true);
	}

    @Test
    // TODO
	// A test to see that no user found with query parameters returns a 422.
	void testFlagsNoUserFound() {
		Assertions.assertTrue(true);
	}
}
