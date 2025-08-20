package com.example.newsfeedproject.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * 사용자 패스워드 인코더 클래스
 */
@Component
public class PasswordEncoder {

    /**
     * 사용자 패스워드 인코드
     * @param rawPassword 인코드할 패스워드
     */
    public String encode(String rawPassword) {

        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * rawPassword와 encodedPassword가 일치하는 경우 true, 아니면 false 반환
     * @param rawPassword 확인하기 위한 패스워드
     * @param encodedPassword : 암호화된 패스워드
     */
    public boolean matches(String rawPassword, String encodedPassword) {

        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
