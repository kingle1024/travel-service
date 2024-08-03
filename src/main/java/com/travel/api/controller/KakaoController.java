package com.travel.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.api.common.UtilityTravel;
import com.travel.api.service.UserService;


@RestController
@RequestMapping("/api")
public class KakaoController {

    private final UserService userService;

    @Autowired
    public KakaoController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/kakaoLogin")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> body) {
        final String accessToken = body.get("accessToken");

        RestTemplate restTemplate = new RestTemplate();

        // 카카오 API를 통해 사용자 정보 가져오기
        String userInfoEndpoint = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        Object id = jsonObject.get("id");
        String userId = "kakao@" + id;
        String nickname = jsonObject.getJSONObject("kakao_account")
                                     .getJSONObject("profile")
                                     .getString("nickname");

        UserDetails userDetails = userService.loadUserById("kakao@" + id, nickname);

        String jwtToken = UtilityTravel.generateToken(userId);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", jwtToken);
        responseBody.put("userId", userId);
        responseBody.put("nickname", nickname);
        responseBody.put("userDetails", userDetails);

        return ResponseEntity.ok(responseBody);
    }

}
