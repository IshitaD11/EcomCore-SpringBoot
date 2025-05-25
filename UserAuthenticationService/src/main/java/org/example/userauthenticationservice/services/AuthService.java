package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.userauthenticationservice.models.*;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean signup(String email, String password) throws EmailAlreadyRegisteredException {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    public String login(String email, String password) throws UserNotFoundException, IncorrectEmailOrPassword {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        if(passwordEncoder.matches(password, user.getPassword())) {
            String token = "token";
            return token;
        }
        else
            throw new IncorrectEmailOrPassword();
    }
}
