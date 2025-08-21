package com.example.newsfeedproject.domain.like.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// DB 레벨에서의 유일성 강제
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","post_id"}))
public class Like extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
