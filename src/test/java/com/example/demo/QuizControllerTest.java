package com.example.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
    }

    @DisplayName("quiz(): GET /quiz?code=1이면 응답코드는 201,응답본문은 Created!를 리턴한다.")
    @Test
    public void getQuiz1() throws Exception {

        //given
        final String url = "/quiz";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("code","1")
        );

        //then
        result
                .andExpect(status().isCreated())
                .andExpect(content().string("Created!"));

    }

    @DisplayName("quiz(): GET /quiz?code=2 이면 응답코드는 400,응답본문은 Bad Request!를 리턴한다.")
    @Test
    public void getQuiz2() throws Exception {

        //given
        final String url = "/quiz";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("code","2")
        );

        //then
        result
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));

    }





}
