package com.example.newsfeedproject.domain.hashtag.service;

import com.example.newsfeedproject.domain.hashtag.entity.Hashtag;
import com.example.newsfeedproject.domain.post.entity.Post;

public interface HashtagServiceApi {

    void saveHashtags(Post post);

    void deleteHashtagsByPost(Post post);

    Hashtag findHashtagByName(String tagName);
}
