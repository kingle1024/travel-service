package com.travel.api.service;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() {
        String to = "test@example.com";
        String subject = "[Travel] 문의 메일 도착";
        String body = "문의자 : test@example.com\n테스트 내용";

        emailService.sendEmail(to, subject, body);

        // mailSender의 send 메서드가 호출되었는지 검증
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
