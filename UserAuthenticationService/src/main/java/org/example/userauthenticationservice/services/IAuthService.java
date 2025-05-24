package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;

public interface IAuthService {

    public boolean signup(String username, String password) throws EmailAlreadyRegisteredException;

    public String login(String username, String password) throws UserNotFoundException, IncorrectEmailOrPassword;
}
