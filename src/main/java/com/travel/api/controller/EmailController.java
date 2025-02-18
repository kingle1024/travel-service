package com.travel.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.dto.EmailRequest;
import com.travel.api.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> emailSend(@RequestBody EmailRequest emailRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            final String title = "[Travel] 문의 메일 도착";
            final String content = String.format("문의자 : %s\n%s", emailRequest.getTo(), emailRequest.getBody());
            emailService.sendEmail("kingle1024@gmail.com", title, content);

            response.put("result", "ok");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.put("result", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
