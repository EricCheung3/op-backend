package com.openprice.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Value object for user login credentials: username and password.
 * For Web UI app, username is email.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserLogin {
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;
}
