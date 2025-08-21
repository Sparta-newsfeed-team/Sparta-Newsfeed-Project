package com.example.newsfeedproject;

import com.example.newsfeedproject.domain.post.dto.PostRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class NewsfeedProjectApplicationTests {

    @Autowired
    MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    // @Transactional // 만약 주석처리 할 경우 데이터가 DB에 저장됨
    @DisplayName("AuthController -> signup 테스트")
    void authControllerSignup () throws Exception {

        String path = "/auth/signup";
        SignupRequest signupRequest = new SignupRequest("name", "abcd@gmail.com", 60, "111aaaAAA!!!");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(path)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(signupRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("PostController -> createPost 테스트")
    void postControllerCreatePost () throws Exception {

        String path = "/posts";
        PostRequest postRequest = new PostRequest("제목", "내용");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(path)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(postRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
