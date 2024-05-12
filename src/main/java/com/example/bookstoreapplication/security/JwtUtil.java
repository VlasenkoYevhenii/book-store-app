package com.example.bookstoreapplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String VALIDATION_EXCEPTION_MSG = "The token validation failed";

    @Value("${jwt.expiration:default}")
    private Long expiration;
    private SecretKey secret;

    public JwtUtil(@Value("${jwt.secret:default}") String secretString) {
        secret = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = parseToken(token);
            return !claimsJws.getPayload().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException(VALIDATION_EXCEPTION_MSG, e);
        }
    }

    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token).getPayload();
        return claimsResolver.apply(claims);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) secret)
                .build()
                .parseSignedClaims(token);
    }
}
