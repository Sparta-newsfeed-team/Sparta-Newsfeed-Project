package com.example.newsfeedproject;

import com.example.newsfeedproject.user.dto.SignupRequest;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @DisplayName("AuthController -> signup 테스트")
    void authControllerSignup () throws Exception {

        String path = "/auth/signup";
        SignupRequest signupRequest = new SignupRequest("name", "abcd@gmail.com", 123, "password");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(path)
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(objectMapper.writeValueAsString(signupRequest));

        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
