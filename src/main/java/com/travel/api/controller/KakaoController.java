package com.travel.api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class KakaoController {
    @PostMapping("/kakaoLogin")
    public String kakaoLogin(@RequestBody Map<String, String> body) {
       String accessToken = body.get("accessToken");

       RestTemplate restTemplate = new RestTemplate();

       // 카카오 API를 통해 사용자 정보 가져오기
       String userInfoEndpoint = "https://kapi.kakao.com/v2/user/me";
       HttpHeaders headers = new HttpHeaders();
       headers.setBearerAuth(accessToken);
       HttpEntity<String> entity = new HttpEntity<>(headers);

       ResponseEntity<String> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, String.class);

       // 사용자 정보 처리 (예: 데이터베이스 저장 등)
       return response.getBody();
    }
}
