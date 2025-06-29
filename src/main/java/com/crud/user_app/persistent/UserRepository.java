package com.crud.user_app.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.crud.user_app.persistent.User;

/**
 * An interface to handle database interactions for the User class.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
    List<User> findByName(String name);

    List<User> findByAge(Integer age);

    List<User> findByBirthState(String birthState);
}
