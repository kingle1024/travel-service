package com.travel.api.dto;

import com.travel.api.vo.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
    private User user;
}
