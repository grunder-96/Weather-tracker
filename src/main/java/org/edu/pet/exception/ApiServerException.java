package org.edu.pet.exception;

public class ApiServerException extends RuntimeException {

    public ApiServerException() {
        super("An internal server error occurred while processing your request. " +
                "Please try again later.");
    }

    public ApiServerException(String message) {
        super(message);
    }
}