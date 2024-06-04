package com.n11.project.exception;

public class TalkNotFoundException extends RuntimeException {
    public TalkNotFoundException(String message) {
        super(message);
    }
}