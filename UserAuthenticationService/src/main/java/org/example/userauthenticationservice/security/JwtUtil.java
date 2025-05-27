package org.example.userauthenticationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.example.userauthenticationservice.models.Session;
import org.example.userauthenticationservice.models.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.userauthenticationservice.models.User;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    @Autowired
    private SecretKey secretKey;

    public String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        Long currentTimeInMillis = System.currentTimeMillis();
        claims.put("iat", currentTimeInMillis); // issued At time
        claims.put("exp", currentTimeInMillis + (24 * 60 * 60 * 1000));
        claims.put("user_id", user.getId());
        claims.put("issuer", "ECom");

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();
        return token;
    }

    public boolean verifyToken(Long userId,String token) {

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiry = (Long)claims.get("exp");
        Long currentTimeStamp = System.currentTimeMillis();

        if(currentTimeStamp > expiry) {
            return true;
        }

        return false;
    }
}
