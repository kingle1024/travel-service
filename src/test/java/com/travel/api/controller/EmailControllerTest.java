package com.travel.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.api.dto.EmailRequest;
import com.travel.api.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class EmailControllerTest {
    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    public void testEmailSendSuccess() throws Exception {
        EmailRequest emailRequest = new EmailRequest();

        mockMvc.perform(post("/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("ok"));

        verify(emailService, times(1)).sendEmail(eq("kingle1024@gmail.com"), anyString(), anyString());
    }

    @Test
    public void testEmailSendFailure() throws Exception {
        EmailRequest emailRequest = new EmailRequest();

        doThrow(new RuntimeException("Email sending failed")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        mockMvc.perform(post("/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.result").value("error"))
                .andExpect(jsonPath("$.message").value("Email sending failed"));
    }
}
