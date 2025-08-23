package com.example.newsfeedproject.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    // 토큰 발급
    public String createToken(long userId) {

        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        long TOKEN_VALIDITY_IN_SECONDS = 1000 * 60 * 60;

        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(new Date(TOKEN_VALIDITY_IN_SECONDS))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
}
