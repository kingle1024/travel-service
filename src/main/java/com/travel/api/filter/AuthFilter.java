package com.travel.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travel.api.security.AllowedPaths;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final String SECRET_KEY = "your_secret_key";

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        // 허용된 경로인지 확인
        for (String path : AllowedPaths.getAllowedPaths()) {
            if (requestUri.matches(path.replace("**", ".*"))) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        final String token = getTokenFromRequest(request);
        if (token != null && validateToken(token)) {
            String userId = getUserIdFromToken(token);
            request.setAttribute("userId", userId);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

