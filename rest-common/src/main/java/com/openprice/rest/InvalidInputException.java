package com.openprice.rest;

/**
 * Used by validation framework, to indicate user input to RESTful API is invalid.
 */
@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(final String message) {
        super(message);
    }
}
