package com.travel.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.api.common.UtilityTravel;
import com.travel.api.dto.TokenResponse;
import com.travel.api.exception.InvalidTokenException;

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
        validateRefreshToken(userId, refreshToken);
        utilityTravel.validToken(refreshToken);

        // 새로운 Access Token 및 Refresh Token 생성
        String newAccessToken = utilityTravel.generateToken(userId, false);
        String newRefreshToken = utilityTravel.generateToken(userId, true);

        // Redis에 새로운 Refresh Token 저장
        tokenService.saveRefreshToken(userId, refreshToken);

        return createTokenResponse(newAccessToken, newRefreshToken);
    }
    private void validateRefreshToken(String userId, String refreshToken) {
        String storedRefreshToken = tokenService.getRefreshToken(userId);
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidTokenException("Invalid Refresh Token");
        }
    }
    private TokenResponse createTokenResponse(String accessToken, String refreshToken) {
        TokenResponse response = new TokenResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
