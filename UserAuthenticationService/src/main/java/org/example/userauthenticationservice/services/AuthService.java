package org.example.userauthenticationservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;
import org.example.userauthenticationservice.repositories.SessionRepository;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.userauthenticationservice.models.*;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private SessionRepository sessionRepo;

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

//            MacAlgorithm algorithm = Jwts.SIG.HS256;
//            SecretKey secretKey = algorithm.key().build();

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

    public boolean verifyToken(Long userId,String token) {
        Optional<Session> optionalSession = sessionRepo.findByTokenAndUser_Id(token,userId);

        if(optionalSession.isEmpty()) {
            System.out.println("Token or userId not found");
            return false;
        }

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiry = (Long)claims.get("exp");
        Long currentTimeStamp = System.currentTimeMillis();

        if(currentTimeStamp > expiry) {
            System.out.println(expiry);
            System.out.println(currentTimeStamp);
            System.out.println("Token is expired");

            //Marking session entry as expired
            optionalSession.get().setSessionState(SessionState.EXPIRED);
            sessionRepo.save(optionalSession.get());
            return false;
        }

        return true;
    }
}
