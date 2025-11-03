package com.mahwi.backend.auth.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * Service for generating and validating JWT tokens.
 */
@Service
public class JwtService {
    private static final String SECRET_KEY = "mahwi_secret_key";

    /**
     * Generates a JWT token with 10-hour validity.
     *
     * @param username the username to encode in the token
     * @return a signed JWT token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
                
            
    }
    

    /**
     * Extracts username from a given JWT token.
     */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
