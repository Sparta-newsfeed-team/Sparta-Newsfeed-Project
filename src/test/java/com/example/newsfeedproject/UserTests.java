package com.example.newsfeedproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("UserController -> getUserInfo 테스트")
    void userControllerGetUserInfoByUserSession () throws Exception {

        String path = "/users/profile";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("LOGIN_USER", 3L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(path)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .session(session);

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("UserController -> getUserInfo 테스트")
    void userControllerGetUserInfoByUserId () throws Exception {

        String path = "/users/{userId}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(path, 3L)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
