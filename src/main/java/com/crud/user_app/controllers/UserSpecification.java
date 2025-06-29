package com.crud.user_app.controllers;

import com.crud.user_app.persistent.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * A specification class allowing for query parameters on the GET endpoint
 */
public class UserSpecification {

    // Accounts for "name" key
    public static Specification<User> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.equal(root.get("name"), name);
    }

    // Accounts for "age" key
    public static Specification<User> hasAge(Integer age) {
        return (root, query, cb) ->
                age == null ? null : cb.equal(root.get("age"), age);
    }

    // Accounts for "birthState" key
    public static Specification<User> hasBirthState(String birthState) {
        return (root, query, cb) ->
                birthState == null ? null : cb.equal(root.get("birthState"), birthState);
    }

    // Accounts for "id" key
    public static Specification<User> hasId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("id"), id);
    }
}
