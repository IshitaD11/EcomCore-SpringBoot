package org.example.userauthenticationservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.userauthenticationservice.models.*;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

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
    public Pair<Boolean,String> login(String email, String password) throws UserNotFoundException, IncorrectEmailOrPassword {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        if(passwordEncoder.matches(password, user.getPassword())) {

            MacAlgorithm algorithm = Jwts.SIG.HS256;
            SecretKey secretKey = algorithm.key().build();
            Map<String,Object> claims = new HashMap<>();
            Long currentTimeInMillis = System.currentTimeMillis();
            claims.put("iat", currentTimeInMillis); // issued At time
            claims.put("exp", currentTimeInMillis + (24 * 60 * 60 * 1000));
            claims.put("user_id", user.getId());
            claims.put("issuer", "ECom");

            String token = Jwts.builder().claims(claims).signWith(secretKey).compact();
            return new Pair<>(true,token);
        }
        else
            throw new IncorrectEmailOrPassword();
    }
}
