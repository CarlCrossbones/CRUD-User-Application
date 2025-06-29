package com.crud.user_app.persistent;

import lombok.Getter;

/**
 * A class controlling the format for request bodies to the DELETE endpoint
 */
public class UserDelete {
    @Getter
    private String name;
    @Getter
    private Long id;
}
