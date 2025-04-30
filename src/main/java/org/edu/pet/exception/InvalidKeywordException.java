package org.edu.pet.exception;

public class InvalidKeywordException extends RuntimeException {

    public InvalidKeywordException() {
        super("Parameter \"keyword\" not found or blank.");
    }

    public InvalidKeywordException(String message) {
        super(message);
    }
}