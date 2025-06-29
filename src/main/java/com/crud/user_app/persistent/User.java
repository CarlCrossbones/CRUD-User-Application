package com.crud.user_app.persistent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A persistent User object representing an entry in the database.
 * Each User should have a name, age, and birthState.
 */
@Entity
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    
    /**
     * The id representing the objects place in the database.
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
    private int age;

    @NotNull
    @Getter
    @Setter
    private String birthState;

    public User (String name, int age, String birthState) {
        this.name = name;
        this.age = age;
        this.birthState = birthState;
    }

}
