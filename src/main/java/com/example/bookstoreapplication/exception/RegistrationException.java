package com.example.bookstoreapplication.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String message) {
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
