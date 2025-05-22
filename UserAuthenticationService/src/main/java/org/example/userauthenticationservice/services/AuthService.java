package org.example.userauthenticationservice.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Override
    public boolean signup(String username, String password) {
        return false;
    }
}
