package com.example.newsfeedproject.post.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    private Long likesCount;

    // @Version 필드는 JPA가 내부적으로 사용하기 때문에, 실제 코드에서 직접 접근하지 않아도 동작합니다.
    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updatePostContent(String content) {
        this.content = content;
    }

    public void increaseLikes() {
        this.likesCount++;
    }

    public void decreaseLikes() {
        this.likesCount = Math.max(0, this.likesCount - 1);
    }
}
