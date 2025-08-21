package com.example.newsfeedproject.domain.user.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private Integer age;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isUsable = true;

    private User(String name, String email, Integer age, String password) {

        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public static User of(String name, String email, Integer age, String password) {

        return new User(name, email, age, password);
    }

    public void updateUserInfo(String name, Integer age) {
        if (name != null)
            this.name = name;

        if (age != null)
            this.age = age;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void markAsWithdrawn(){
        this.isUsable = false;
    }
}
