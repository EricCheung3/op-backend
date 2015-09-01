package com.openprice.security.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * Value object for user login credentials: username and password.
 *
 */
public class UserLogin {
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;
}
