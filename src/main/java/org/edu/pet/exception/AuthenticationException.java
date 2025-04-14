package org.edu.pet.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Invalid username or password.");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}