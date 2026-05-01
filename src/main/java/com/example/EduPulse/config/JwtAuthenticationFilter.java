package com.example.EduPulse.config;



import com.example.EduPulse.security.CookieBuilder;
import com.example.EduPulse.user.JwtUser;
import com.example.EduPulse.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CookieBuilder cookieBuilder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Skip token validation for authentication endpoints
        String path = request.getRequestURI();
        if (path.contains("/auth/") || path.contains("/public/") || path.contains("/api/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getAccessTokenFromCookie(request);
        String refreshToken = getRefreshTokenFromCookie(request);

        // Case 1: No tokens present
        if (accessToken == null && refreshToken == null) {
            log.debug("No tokens found, continuing without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        // Case 2: Access token is valid (not expired)
        if (accessToken != null && jwtUtils.validateToken(accessToken) && jwtUtils.isAccessToken(accessToken)) {
            log.debug("Access token is valid, authenticating user");
            authenticateUser(accessToken, request);

            // If access token is valid, ALWAYS generate a new refresh token
            // This keeps the user logged in indefinitely as long as they're active
            if (refreshToken != null && jwtUtils.validateToken(refreshToken) && jwtUtils.isRefreshToken(refreshToken)) {
                String newRefreshToken = jwtUtils.generateNewRefreshToken(refreshToken);
                if (newRefreshToken != null) {
                    cookieBuilder.setRefreshTokenCookie(response, newRefreshToken);
                    log.debug("Refresh token rotated successfully");
                }
            }

            filterChain.doFilter(request, response);
            return;
        }

        // Case 3: Access token expired but refresh token exists and is valid
        if (refreshToken != null && jwtUtils.validateToken(refreshToken) && jwtUtils.isRefreshToken(refreshToken)) {
            log.debug("Access token expired, attempting to refresh using refresh token");

            // Generate new access token from refresh token
            String newAccessToken = jwtUtils.generateNewAccessTokenFromRefreshToken(refreshToken);

            if (newAccessToken != null) {
                // Generate a NEW refresh token as well (token rotation for security)
                String newRefreshToken = jwtUtils.generateNewRefreshToken(refreshToken);

                // Set both new tokens in cookies
                if (newRefreshToken != null) {
                    cookieBuilder.setAccessTokenCookie(response, newAccessToken);
                    cookieBuilder.setRefreshTokenCookie(response, newRefreshToken);
                }

                // Authenticate with new access token
                authenticateUser(newAccessToken, request);

                log.debug("Both access and refresh tokens rotated successfully");
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Case 4: Refresh token expired or invalid
        // Clear all cookies and do NOT authenticate
        log.debug("Refresh token expired or invalid, clearing cookies");
        cookieBuilder.clearCookies(response);
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token, HttpServletRequest request) {
        try {
            String userId = jwtUtils.extractUserId(token);
            String username = jwtUtils.extractUsername(token);
            String email = jwtUtils.extractEmail(token);
            String role = jwtUtils.extractRole(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                JwtUser jwtUser = JwtUser.builder()
                        .id(userId)
                        .email(email)
                        .username(username)
                        .role(role)
                        .build();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                jwtUser,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("User {} authenticated successfully", username);
            }
        } catch (JwtException ex) {
            log.error("Failed to authenticate user: {}", ex.getMessage());
        }
    }

    private String getAccessTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
