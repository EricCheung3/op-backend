package com.openprice.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestErrorHandler {

    /**
     * Return 404 Not Found if resource cannot be found.
     *
     * @param ex
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(final ResourceNotFoundException ex) {
        log.debug("handling 404 error on a resource");
    }

    /**
     * Return 401 Unauthorized if user provided wrong credentials.
     *
     * @param ex
     */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleAuthenticationCredentialsNotFoundException(final AuthenticationCredentialsNotFoundException ex) {
        log.debug("User provided wrong credentials.");
    }

    /**
     * Return 401 Unauthorized if user account locked.
     *
     * @param ex
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleBadCredentialsException(final BadCredentialsException ex) {
        log.debug("User account locked.");
    }

    /**
     * Returns 403 Forbidden if user doesn't have privilege to access certain resources.
     *
     * @param ex
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(final AccessDeniedException ex) {
        log.debug("handling access secure resource without privilege");
    }

}
