package com.example.newsfeedproject.user.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Column(nullable = true, length = 3)
    private int age;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isUsable = true;

    public User(String name, String email, int age, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.isUsable = true;
    }

    public void updateInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void markAsWithdrawn(){
        this.isUsable = false;
    }
}
