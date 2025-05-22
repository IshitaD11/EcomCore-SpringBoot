package org.example.userauthenticationservice.services;

public interface IAuthService {

    public boolean signup(String username, String password);

    public boolean login(String username, String password);
}
