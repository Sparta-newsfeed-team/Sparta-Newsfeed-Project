package com.example.newsfeedproject.domain.hashtag.service;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.hashtag.entity.Hashtag;
import com.example.newsfeedproject.domain.hashtag.entity.PostHashtag;
import com.example.newsfeedproject.domain.hashtag.repository.HashtagRepository;
import com.example.newsfeedproject.domain.hashtag.repository.PostHashtagRepository;
import com.example.newsfeedproject.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class HashtagService implements HashtagServiceApi {

    private final HashtagRepository hashtagRepository;
    private final PostHashtagRepository postHashtagRepository;

    @Transactional
    public void saveHashtags(Post post) {

        List<String> tagNames = findHashTagByContent(post.getContent());

        if (tagNames.isEmpty()) return;

        for (String tagName : tagNames) {
            Hashtag hashtag = hashtagRepository.findByName(tagName)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(tagName)));

            PostHashtag postHashtag = new PostHashtag(post, hashtag);
            postHashtagRepository.save(postHashtag);
        }
    }

    @Override
    public void deleteHashtagsByPost(Post post) {

        postHashtagRepository.deleteAllByPost(post);
    }

    @Override
    public Hashtag findHashtagByName(String tagName) {

        return hashtagRepository.findByName(tagName)
                .orElseThrow(() -> new BusinessException(ErrorCode.HASHTAG_NOT_FOUND));
    }

    private List<String> findHashTagByContent(String content) {

        Pattern pattern = Pattern.compile("#[^\\s#]+");
        Matcher matcher = pattern.matcher(content);
        List<String> tags = new ArrayList<>();

        while (matcher.find()) {
            tags.add(matcher.group(1));
        }

        return tags;
    }
}
