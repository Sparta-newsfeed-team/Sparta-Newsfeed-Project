package com.example.newsfeedproject.common.validator;

import com.example.newsfeedproject.common.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 비밀번호 유효성 검사를 수행하는 Validator 클래스
 */
@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    // 비밀번호 정규식 패턴
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";

    // 유효성 검사 로직
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        // password가 null이 아니고 정규식 패턴과 일치하는 경우에만 true 반환
        return Objects.nonNull(password) && password.matches(PASSWORD_PATTERN);
    }
}
