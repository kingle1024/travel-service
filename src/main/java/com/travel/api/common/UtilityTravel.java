package com.travel.api.common;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UtilityTravel {
    private static final String SECRET_KEY = "your_secret_key";

    public static String maskAuthor(String item) {
        if (item == null || item.length() <= 1) {
            return item; // null이거나 길이가 1 이하인 경우 그대로 반환
        }

        // 첫 글자를 제외한 나머지 부분을 '*'로 변환
        StringBuilder maskedAuthor = new StringBuilder();
        maskedAuthor.append(item.charAt(0));

        for (int i = 1; i < item.length(); i++) {
            maskedAuthor.append('*');
        }

        return maskedAuthor.toString();
    }


    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1시간 후 만료
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
}
