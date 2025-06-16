package org.example.userauthenticationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.example.userauthenticationservice.clients.KafkaClient;
import org.example.userauthenticationservice.dtos.EmailDto;
import org.example.userauthenticationservice.exceptions.EmailAlreadyRegisteredException;
import org.example.userauthenticationservice.exceptions.IncorrectEmailOrPassword;
import org.example.userauthenticationservice.exceptions.UserNotFoundException;
import org.example.userauthenticationservice.repositories.SessionRepository;
import org.example.userauthenticationservice.repositories.UserRepository;
import org.example.userauthenticationservice.security.JwtUtil;
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
    private SessionRepository sessionRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User signup(String email, String password) throws EmailAlreadyRegisteredException {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        try {
            String topic = "user_signup";
            EmailDto emailDto = new EmailDto();
            emailDto.setFrom("springbootecom@gmail.com");
            emailDto.setTo(email);
            emailDto.setSubject("Welcome to EService");
            emailDto.setBody("Have a pleasant shopping experience.");
            String message = objectMapper.writeValueAsString(emailDto);
            kafkaClient.sendMessage(topic,message);
            System.out.println("Sending message to Kafka: " + message);

        }catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return user;
    }

    @Override
    public Pair<Boolean,String> login(String email, String password) throws UserNotFoundException, IncorrectEmailOrPassword {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        if(passwordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateToken(user);
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


        if(jwtUtil.verifyToken(userId, token)) {
            System.out.println("Token is expired");

            //Marking session entry as expired
            optionalSession.get().setSessionState(SessionState.EXPIRED);
            sessionRepo.save(optionalSession.get());
            return false;
        }

        return true;
    }
}
