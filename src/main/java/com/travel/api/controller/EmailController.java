package com.travel.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.dto.EmailRequest;
import com.travel.api.service.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email/send")
    public ResponseEntity<Map<String, Object>> emailSend(@RequestBody EmailRequest emailRequest) {
        try {
            final String title = "[Travel] 문의 메일 도착";
            final String content = "문의자 : " + emailRequest.getTo() + "\n" + emailRequest.getBody();
            emailService.sendEmail("kingle1024@gmail.com", title, content);

            Map<String, Object> results = new HashMap<>();
            results.put("result", "ok");
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("result", "error");
            errorResult.put("message", e.getMessage());
            return ResponseEntity.status(500).body(errorResult);
        }
    }
}
