package org.edu.pet.exception;

public class InvalidSessionIdException extends RuntimeException {

    public InvalidSessionIdException() {
        super("The value is not a session identifier.");
    }

    public InvalidSessionIdException(String message) {
        super(message);
    }
}