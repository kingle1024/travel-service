package com.travel.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.api.common.UtilityTravel;
import com.travel.api.dto.TokenResponse;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final UtilityTravel utilityTravel;

    @Autowired
    public AuthService(TokenService tokenService, UtilityTravel utilityTravel) {
        this.tokenService = tokenService;
        this.utilityTravel = utilityTravel;
    }

    public TokenResponse refreshAccessToken(String userId, String refreshToken) {
        // Redis에서 Refresh Token으로 사용자 이름을 가져옴
        String getRedisToken = tokenService.getRefreshToken(userId);
        if (!refreshToken.equals(getRedisToken)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        // JWT 서명 검증 및 만료 시간 확인
        utilityTravel.validToken(refreshToken);

        // 새로운 Access Token 및 Refresh Token 생성
        String newAccessToken = utilityTravel.generateToken(userId, false);
        String newRefreshToken = utilityTravel.generateToken(userId, true);

        // Redis에 새로운 Refresh Token 저장
        tokenService.saveRefreshToken(userId, refreshToken);

        // TokenResponse 객체 생성 및 반환
        TokenResponse response = new TokenResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);
        return response;
    }
}
