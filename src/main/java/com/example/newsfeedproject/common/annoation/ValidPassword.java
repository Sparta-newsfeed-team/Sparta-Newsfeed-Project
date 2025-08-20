package com.example.newsfeedproject.common.annoation;

import com.example.newsfeedproject.common.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 비밀번호 유효성 검사를 위한 커스텀 어노테이션<br>
 * - 최소 8자, 최대 20자<br>
 * - 영문 대소문자, 숫자, 특수문자 각각 최소 1개 포함
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "비밀번호는 8~20자이며, 대소문자/숫자/특수문자를 최소 1글자씩 포함해야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
