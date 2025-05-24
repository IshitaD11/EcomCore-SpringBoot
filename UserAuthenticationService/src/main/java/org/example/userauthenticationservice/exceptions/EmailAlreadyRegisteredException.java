package org.example.userauthenticationservice.exceptions;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException() {
        super("Email already registered");
    }
}
