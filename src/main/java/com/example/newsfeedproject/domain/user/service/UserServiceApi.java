package com.example.newsfeedproject.domain.user.service;

import com.example.newsfeedproject.domain.user.entity.User;

public interface UserServiceApi {

    /**
     * id 값으로 User를 찾아서 반환
     * @param id User id
     */
    User findUserByIdOrElseThrow(Long id);

    /**
     * email 값으로 User를 찾아서 반환
     * @param email User email
     */
    User findUserByEmail(String email);

    /**
     * 해당 email을 가진 User가 있는 경우 ErrorCode.EMAIL_ALREADY_EXISTS 예외 처리
     * @param email User email
     */
    void checkExistsUserByEmail(String email);

    /**
     * User Repository에 User 저장
     * @param user User
     */
    void saveUser(User user);
}
