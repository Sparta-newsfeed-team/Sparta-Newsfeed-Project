package com.example.newsfeedproject.common.config;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.common.security.JwtUtil;
import com.example.newsfeedproject.domain.user.entity.User;
import com.example.newsfeedproject.domain.user.service.UserServiceApi;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserServiceApi userService;
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String header = request.getHeader("Authorization");
        if (Objects.isNull(header) || !header.startsWith("Bearer "))
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);

        String token = header.substring("Bearer ".length());

        try {
            long userId = jwtUtil.getUserIdFromToken(token);
            User user = userService.findUserByIdOrElseThrow(userId);
            if (!user.isUsable())
                throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);

            return user;
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }
}