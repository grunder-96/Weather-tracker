package org.edu.pet.exception;

public class SessionNotFoundOrExpiredException extends RuntimeException{

    public SessionNotFoundOrExpiredException() {
        super("Session not found or expired.");
    }

    public SessionNotFoundOrExpiredException(String message) {
        super(message);
    }
}