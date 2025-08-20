package com.example.newsfeedproject.common.validator;

import com.example.newsfeedproject.common.annoation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

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
        if (password == null) {
            return false;
        }
        return password.matches(PASSWORD_PATTERN);
    }
}
