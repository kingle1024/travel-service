package com.travel.api.service;

import com.travel.api.common.UtilityTravel;
import com.travel.api.dto.TokenResponse;
import com.travel.api.exception.InvalidTokenException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UtilityTravel utilityTravel;

    private String userId;
    private String refreshToken;
    private String validRefreshToken;
    private TokenResponse expectedResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = "testUser";
        refreshToken = "refreshToken";
        validRefreshToken = "validRefreshToken";

        expectedResponse = new TokenResponse();
        expectedResponse.setAccessToken("newAccessToken");
        expectedResponse.setRefreshToken("newRefreshToken");
    }

    @Test
    public void testRefreshAccessToken_Success() {
        when(tokenService.getRefreshToken(userId)).thenReturn(refreshToken);
        doNothing().when(utilityTravel).validToken(refreshToken);
        when(utilityTravel.generateToken(userId, false)).thenReturn("newAccessToken");
        when(utilityTravel.generateToken(userId, true)).thenReturn("newRefreshToken");
        doNothing().when(tokenService).saveRefreshToken(userId, "newRefreshToken");

        TokenResponse response = authService.refreshAccessToken(userId, refreshToken);

        assertEquals(expectedResponse.getAccessToken(), response.getAccessToken());
        assertEquals(expectedResponse.getRefreshToken(), response.getRefreshToken());
        verify(tokenService).getRefreshToken(userId);
        verify(utilityTravel).validToken(refreshToken);
        verify(utilityTravel).generateToken(userId, false);
        verify(utilityTravel).generateToken(userId, true);
        verify(tokenService).saveRefreshToken(userId, refreshToken);
    }

    @Test
        public void testRefreshAccessToken_InvalidToken() {
            when(tokenService.getRefreshToken(userId)).thenReturn("invalidToken");

            InvalidTokenException thrown = assertThrows(InvalidTokenException.class, () -> {
                authService.refreshAccessToken(userId, refreshToken);
            });

            assertEquals("Invalid Refresh Token", thrown.getMessage());
        }

}
