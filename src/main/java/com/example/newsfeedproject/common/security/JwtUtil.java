package com.example.newsfeedproject.common.security;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor("64K07J2867Cw7JuA7Lqg7ZSEMTDsobDribTsiqTtlLzrk5ztlITroZzsoJ3tirg".getBytes(StandardCharsets.UTF_8));

    // 토큰 발급
    public String createToken(long userId) {

        Date now = new Date();
        long TOKEN_VALIDITY_IN_SECONDS = 1000 * 60 * 60;

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_VALIDITY_IN_SECONDS))
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    // 토큰으로 사용자 id 값 반환
    public long getUserIdFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        try {
            return Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
