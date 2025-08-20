package com.example.newsfeedproject.user.repository;

import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /**
     * 사용자 ID를 조회하며, 없을 경우 예외를 발생시킵니다.
     */
    default User findByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    }
}
