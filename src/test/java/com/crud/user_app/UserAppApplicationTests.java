package com.crud.user_app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

/**
 * A test suite for ensuring connection to the database
 */
@SpringBootTest
class UserAppApplicationTests {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Test Spring connects to the database
	 */
	@Test
	void testDatabaseConnected() {
		Object result = entityManager.createNativeQuery("SELECT 1").getSingleResult();
		Assertions.assertEquals(1, ((Number) result).intValue());
	}
}
