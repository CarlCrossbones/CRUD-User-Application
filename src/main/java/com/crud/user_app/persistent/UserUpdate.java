package com.crud.user_app.persistent;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * A class controlling the format for request bodies to the UPDATE endpoint
 */
public class UserUpdate {
    @Getter
    private Long id;
    @Getter
    private String name;
    @NotNull
    @Getter
    private UpdateFields update;

    public static class UpdateFields {
        @Getter
        private String name;
        @Getter
        private Integer age;
        @Getter
        private String birthState;
    }
}
