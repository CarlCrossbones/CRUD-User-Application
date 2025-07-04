package com.crud.user_app.persistent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

/**
 * A persistent User object representing an entry in the database.
 * Each User should have a name, age, id, and birthState.
 */
@Entity
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    
    /**
     * The id representing the object's place in the database.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Getter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @NotNull
    @Getter
    @Setter
    private Integer age;

    @NotNull
    @Getter
    @Setter
    private String birthState;

    public User (String name, Integer age, String birthState) {
        this.name = name;
        this.age = age;
        this.birthState = birthState;
    }

}
