package com.example.newsfeedproject.common.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User Resolver<br>
 * - Session의 LOGIN_USER 값을 User 객체로 매핑하여 반환<br>
 * - 참고 : common/config/UserHandlerArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUserResolver {
}
