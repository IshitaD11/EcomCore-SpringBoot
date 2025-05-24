package org.example.userauthenticationservice.exceptions;

public class IncorrectEmailOrPassword extends Exception {
    public IncorrectEmailOrPassword() {
        super("Incorrect email or password");
    }
}
