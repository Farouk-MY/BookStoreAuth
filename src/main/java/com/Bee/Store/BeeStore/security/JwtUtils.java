package com.Bee.Store.BeeStore.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    // Method to get a secure signing key
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(); // Convert the string to bytes
        // Ensure the key length is 32 bytes (256 bits) for HS512
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The JWT secret must be at least 256 bits (32 bytes) long.");
        }
        return Keys.hmacShaKeyFor(keyBytes); // Generate the signing key
    }

    public String generateJwtToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey()) // Use the secure signing key
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Use the secure signing key
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey()) // Use the secure signing key
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
