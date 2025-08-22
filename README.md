# 📺 팀 10시 뉴스 - 뉴스피드(Newsfeed) 프로젝트

> **프로젝트 목표**  
> : 팔로우/팔로워, 게시물 좋아요, 북마크, 해시태그 등 다양한 SNS 기능을 갖춘 효율적인 뉴스피드 백엔드 API 서버를 개발

---

## 🛠️ 기술 스택

### 1. 개발 언어 & 런타임
- **Language** : Java 17
- **JDK** : Amazon Corretto 17

### 2. 프레임워크
- **Framework** : Spring Boot 3.5.4

### 3. 개발 도구
- **IDE** : IntelliJ IDEA
- **Version Control** : Git

### 4. 주요 라이브러리 / 의존성
- **Spring Web** : REST API 개발
- **MapStruct** : DTO <-> Entity 매핑
- **Validation** : Request 검증
- **BCrypt** : 사용자 비밀번호 암호화
- **JPA** : 데이터베이스 연동
- **Lombok** : 코드 생산성 향상

---

## ✨ 주요 기능

<details>
<summary>👤 프로필 관리</summary>

- 프로필 조회 기능: 다른 사용자의 프로필 조회 / 내 프로필 조회
- 프로필 수정 기능: 로그인한 사용자가 자신의 정보 수정
- 비밀번호 수정 기능: 현재 비밀번호를 정확히 입력해야만 새 비밀번호 변경
</details>

<details>
<summary>📄 뉴스피드 게시물 관리 / 검색 및 정렬 기능</summary>

- 게시물 작성, 조회, 수정, 삭제 기능
    - 게시물 수정, 삭제: 본인만 가능
    - 게시물 조회: 생성일자 기준 내림차순 정렬 / 10개씩 페이지네이션
    - 기간별 게시물 조회: 시작 날짜와 마지막 날짜를 골라 해당 날짜에 작성된 게시물 조회
</details>

<details>
<summary>⚙ 사용자 인증 및 로그인</summary>

- 회원가입 기능
    - 사용자 아이디: 이메일 형식
    - 비밀번호: BCrypt 로 인코딩
- 회원탈퇴 기능
    - 탈퇴 처리 시 비밀번호 확인 후 일치할 때 탈퇴 처리
    - 탈퇴한 사용자 아이디 재사용 및 복구 불가
- 로그인 기능
    - Session 기반 인증 처리
</details>

<details>
<summary>🙋‍♀️ 팔로우 기능 (친구 관리)</summary>

- 팔로우: 특정 사용자를 팔로우
- 언팔로우: 특정 사용자를 언팔로우
- 팔로우/팔로잉 목록 조회: 내가 팔로잉 한 사용자 / 나를 팔로우 한 사용자 조회
- 팔로우 기반 뉴스피드 조회: 내가 팔로우 하고 있는 사용자의 게시물 조회
</details>

<details>
<summary>💬 댓글 기능</summary>

- 댓글 작성, 조회, 수정, 삭제 기능
    - 댓글 수정, 삭제: 댓글 작성한 본인 또는 해당 댓글이 달린 게시글의 작성자만 가능
</details>

<details>
<summary>👍 좋아요 및 해시태그 기능 </summary>

- 댓글 작성, 조회, 수정, 삭제 기능
    - 댓글 수정, 삭제: 댓글 작성한 본인 또는 해당 댓글이 달린 게시글의 작성자만 가능
</details>

<details>
<summary>📕 북마크 기능</summary>

- 북마크 추가 / 삭제 / 조회 기능
    - 추가 및 삭제: 사용자는 원하는 게시물에 북마크를 추가하거나 삭제
    - 조회: 본인이 북마크한 게시물 조회 (10개씩 표시)
</details>

---

## 📜 ERD 명세서

![ERD](/.github/assets/ERD_img.png)

---

## 📄 API 명세서

- Auth

| Feature | METHOD | ENDPOINT         | Request                    |
|---------|--------|------------------|----------------------------|
| 회원 가입   | POST   | `/auth/signup`   | name, email, age, password |
| 로그인     | POST   | `/auth/login`    | email, password            |
| 로그 아웃   | POST   | `/auth/logout`   |                            |
| 회원 탈퇴   | DELETE | `/auth/withdraw` |                            |

- User

| Feature  | METHOD | ENDPOINT          | Request                      |
|----------|--------|-------------------|------------------------------|
| 유저 정보 조회 | GET    | `/users/{userId}` |                              |
| 내 프로필 수정 | PATCH  | `/users`          | name, age                    |
| 내 프로필 조회 | GET    | `/users/profile`  |                              |
| 비밀번호 수정  | PATCH  | `/users/password` | currentPassword, newPassword |

- Post

| Feature           | METHOD | ENDPOINT                                         | Request        |
|-------------------|--------|--------------------------------------------------|----------------|
| 게시글 작성            | POST   | `/posts`                                         | title, content |
| 팔로우 기반 뉴스피드 조회    | GET    | `/posts/feed?page=`                              |                |
| 전체 게시글 조회         | GET    | `/posts?page=`                                   |                |
| 단건 게시글 조회         | GET    | `/posts/{postId}`                                |                |
| 기간별 게시글 조회        | GET    | `/posts/search/period?startDate=&endDate=&page=` |                | 
| 게시글 내용 수정         | PATCH  | `/posts/{postId}`                                | content        |
| 게시글 삭제            | DELETE | `/posts/{postId}`                                |                |
| 게시글 좋아요 추가        | POST   | `/posts/{postId}/likes`                          |                |
| 게시글 좋아요 취소        | DELETE | `/posts/{postId}/likes`                          |                |
| 게시글에 좋아요 누른 유저 조회 | GET    | `/posts/{postId}/likes`                          |                |

- Comment

| Feature       | METHOD | ENDPOINT                               | Request |
|---------------|--------|----------------------------------------|---------|
| 댓글 생성         | POST   | `/posts/{postId}/comments`             | content |
| 댓글 수정         | PATCH  | `/posts/{postId}/comments/{commentId}` | content |
| 댓글 삭제         | DELETE | `/posts/{postId}/comments/{commentId}` |         |
| 게시글에 달린 댓글 조회 | GET    | `/posts/{postId}/comments`             |         |

- BookMark

| Feature | METHOD | ENDPOINT                    | Request |
|---------|--------|-----------------------------|---------|
| 북마크 조회  | GET    | `/bookmarks?page=`          |         | 
| 북마크 추가  | POST   | `/posts/{postId}/bookmarks` |         | 
| 북마크 삭제  | DELETE | `/posts/{postId}/bookmarks` |         |

- Follow

| Feature   | METHOD  | ENDPOINT                 | Request |
|-----------|---------|--------------------------|---------|
| 팔로우       | POST	   | `/users/{userId}/follow` |         | 
| 언팔로우      | DELETE	 | `/users/{userId}/follow` |         | 
| 팔로잉 목록 조회 | GET	    | `/users/following`       |         | 
| 팔로워 목록 조회 | GET	    | `/users/followers`       |         |

- Hashtag

| Feature         | METHOD | ENDPOINT                          | Request |
|-----------------|--------|-----------------------------------|---------|
| 특정 해시태그의 게시물 조회 | GET    | `/post/search/hashtag?tag=&page=` |         |

---

## 📍 트러블 슈팅

프로젝트를 진행하며 마주쳤던 주요 기술적 문제와 해결 과정을 기록했습니다.

| No. | 문제 상황 요약 |  상세 내용 |
|-----|----------------|--------------|
| 1 | 잘못된 해시태그 DB 설계 | [Link](https://www.notion.so/DB-2575c36cb0a280b0a36bc98960de24c9) |
| 2 | 기간별 게시물 조회 시 바인딩 오류 | [Link](https://www.notion.so/2575c36cb0a2804bb61acf7acf0017ac) |
| 3 | 다른 도메인 서비스 사용 문제 | [Link](https://www.notion.so/2575c36cb0a280f58fb6ef62ade95c2d) |
| 4 | MySQL 접속 시 비밀번호 불일치 에러 |  [Link](https://www.notion.so/MySQL-2575c36cb0a280b8b7a7f1a0060beca8) |
| 5 | MapStruct의 DTO → Entity 매핑 문제 |  [Link](https://www.notion.so/DTO-Entity-2575c36cb0a28011ad48c7deaa7b0aae) |


---

## 📂 디렉토리

```
newsfeedproject
├── common
│   ├── annotation
│   │   ├── LoginUserResolver
│   │   └── ValidPassword
│   │
│   ├── config
│   │   ├── JpaAuditingConfig
│   │   ├── PasswordEncoder
│   │   ├── UserHandlerArgumentResolver
│   │   └── WebConfig
│   │
│   ├── dto
│   │   └── GlobalApiResponse
│   │
│   ├── entity
│   │   └── BaseEntity
│   │
│   ├── exception
│   │   ├── BusinessException
│   │   ├── ErrorCode
│   │   └── GlobalExceptionHandler
│   │
│   └── validator
│       └── PasswordValidator
│
└── domain
    ├── auth
    │   ├── controller
    │   │   └── AuthController
    │   └── service
    │       └── AuthService
    │
    ├── bookmark
    │   ├── controller
    │   │   └── BookmarkController
    │   ├── entity
    │   │   └── Bookmark
    │   ├── repository
    │   │   └── BookmarkRepository
    │   └── service
    │       └── BookmarkService
    │
    ├── comment
    │   ├── controller
    │   │   └── CommentController
    │   ├── dto
    │   │   ├── CommentCreateResponse
    │   │   ├── CommentListResponse
    │   │   ├── CommentRequest
    │   │   └── CommentUpdateResponse
    │   ├── entity
    │   │   └── Comment
    │   ├── mapper
    │   │   └── CommentMapper
    │   ├── repository
    │   │   └── CommentRepository
    │   └── service
    │       └── CommentService
    │
    ├── follow
    │   ├── controller
    │   │   └── FollowController
    │   ├── dto
    │   │   ├── FollowerResponse
    │   │   └── FollowingResponse
    │   ├── entity
    │   │   └── Follow
    │   ├── mapper
    │   │   └── FollowMapper
    │   ├── repository
    │   │   └── FollowRepository
    │   └── service
    │       ├── FollowService
    │       └── FollowServiceApi
    │
    ├── hashtag
    │   ├── entity
    │   │   ├── Hashtag
    │   │   └── PostHashtag
    │   ├── repository
    │   │   ├── HashtagRepository
    │   │   └── PostHashtagRepository
    │   └── service
    │       ├── HashtagService
    │       └── HashtagServiceApi
    │
    ├── like
    │   ├── controller
    │   │   └── LikeController
    │   ├── dto
    │   │   ├── LikeListResponse
    │   │   └── LikeResponse
    │   ├── entity
    │   │   └── Like
    │   ├── repository
    │   │   └── LikeRepository
    │   └── service
    │       └── LikeService
    │
    ├── post
    │   ├── controller
    │   │   └── PostController
    │   ├── dto
    │   │   ├── PostListResponse
    │   │   ├── PostRequest
    │   │   ├── PostResponse
    │   │   └── UpdatePostContentRequest
    │   ├── entity
    │   │   └── Post
    │   ├── mapper
    │   │   └── PostMapper
    │   ├── repository
    │   │   └── PostRepository
    │   └── service
    │       ├── PostService
    │       └── PostServiceApi
    │
    └── user
        ├── controller
        │   └── UserController
        ├── dto
        │   ├── AuthorResponse
        │   ├── DeleteUserRequest
        │   ├── LoginRequest
        │   ├── SignupRequest
        │   ├── UpdatePasswordRequest
        │   ├── UpdateUserInfoRequest
        │   └── UserResponse
        ├── entity
        │   └── User
        ├── mapper
        │   └── UserMapper
        ├── repository
        │   └── UserRepository
        └── service
            ├── UserService
            └── UserServiceApi
 └── NewsfeedProjectApplication
```
