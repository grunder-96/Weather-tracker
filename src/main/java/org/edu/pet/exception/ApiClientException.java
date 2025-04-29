package org.edu.pet.exception;

public class ApiClientException extends RuntimeException {

    public ApiClientException() {
        super("The request could not be processed due to invalid input parameters.");
    }

    public ApiClientException(String message) {
        super(message);
    }
}