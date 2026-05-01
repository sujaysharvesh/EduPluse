package com.example.EduPulse.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieBuilder {

    @Value("${app.jwt.access-cookie-name}")
    private String accessCookieName;

    @Value("${app.jwt.refresh-cookie-name}")
    private String refreshCookieName;

    @Value("${app.jwt.cookie-path}")
    private String cookiePath;

    @Value("${app.jwt.access-cookie-max-age}")
    private long accessCookieMaxAge;

    @Value("${app.jwt.refresh-cookie-max-age}")
    private long refreshCookieMaxAge;

    public void setAccessTokenCookie(
            HttpServletResponse response,
            String token
    ) {

        ResponseCookie cookie = ResponseCookie.from(accessCookieName, token)
                .httpOnly(true)
                .secure(false)
                .path(cookiePath)
                .maxAge(accessCookieMaxAge)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void setRefreshTokenCookie(
            HttpServletResponse response,
            String token
    ) {

        ResponseCookie cookie = ResponseCookie.from(refreshCookieName, token)
                .httpOnly(true)
                .secure(false)
                .path(cookiePath)
                .maxAge(refreshCookieMaxAge)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearCookies(HttpServletResponse response) {

        ResponseCookie accessCookie =
                ResponseCookie.from(accessCookieName, "")
                        .httpOnly(true)
                        .secure(false)
                        .path(cookiePath)
                        .maxAge(0)
                        .sameSite("None")
                        .build();

        ResponseCookie refreshCookie =
                ResponseCookie.from(refreshCookieName, "")
                        .httpOnly(true)
                        .secure(false)
                        .path(cookiePath)
                        .maxAge(0)
                        .sameSite("Lax")
                        .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                accessCookie.toString()
        );

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                refreshCookie.toString()
        );
    }
}