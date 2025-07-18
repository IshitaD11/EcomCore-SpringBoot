package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.InvalidRoleNameException;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice.models.User;

public interface IAuthService {

    public User signup(String email, String password, String role, String fullName, String phoneNo) throws EmailAlreadyRegisteredException, InvalidRoleNameException;

    public Pair<Boolean,String> login(String username, String password) throws UserNotFoundException, IncorrectEmailOrPassword;
}
