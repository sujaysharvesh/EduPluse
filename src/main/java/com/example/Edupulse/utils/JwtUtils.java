package com.example.Edupulse.utils;

import com.example.Edupulse.common.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwt.access-token-expiry-ms:900000}")
    private long accessTokenExpiryMs;  // default 15 minutes

    @Value("${app.jwt.refresh-token-expiry-ms:2592000000}")
    private long refreshTokenExpiryMs; // default 30 days

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate Access Token
    public String generateAccessToken(UUID userId, String email, String username, UserRole role, String schoolId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessTokenExpiryMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "accessToken");
        claims.put("id", userId);
        claims.put("username", username);
        claims.put("email", email);
        claims.put("role", role.name());
        claims.put("schoolId", schoolId);

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(UUID userId, String email, String username, UserRole role,String schoolId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshTokenExpiryMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refreshToken");
        claims.put("id", userId);
        claims.put("username", username);
        claims.put("email", email);
        claims.put("role", role.name());
        claims.put("schoolId", schoolId);


        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    public String generateNewRefreshToken(String oldRefreshToken) {
        String userId = extractUserId(oldRefreshToken);
        String username = extractUsername(oldRefreshToken);
        String email = extractEmail(oldRefreshToken);
        String role = extractRole(oldRefreshToken);
        String schoolId = extractSchoolId(oldRefreshToken);

        if (userId == null || username == null || email == null || role == null) {
            return null;
        }

        // Generate brand new refresh token with fresh 30-day expiry
        UserRole userRole = UserRole.valueOf(role);

        log.debug("Generating new refresh token for user: {}", username);
        return generateRefreshToken(
                UUID.fromString(userId),
                email,
                username,
                userRole,
                schoolId
        );
    }

    public String generateNewAccessTokenFromRefreshToken(String oldRefreshToken) {

        String userId = extractUserId(oldRefreshToken);
        String username = extractUsername(oldRefreshToken);
        String email = extractEmail(oldRefreshToken);
        String role = extractRole(oldRefreshToken);
        String schoolId = extractSchoolId(oldRefreshToken);

        if (userId == null || username == null || email == null || role == null) {
            log.warn("Missing user details in refresh token");
            return null;
        }

        // Generate new access token
        UserRole userRole = UserRole.valueOf(role);

        log.debug("Generating new access token for user: {}", username);
        return generateAccessToken(
                UUID.fromString(userId),
                email,
                username,
                userRole,
                schoolId
        );

    }

    // Extract all claims
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract specific claims
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    public String extractSchoolId(String token) {
        return extractClaim(token, claims -> claims.get("schoolId", String.class));
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate token
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateToken(String token, String userId) {
        try {
            final String extractedUserId = extractUserId(token);
            return (extractedUserId.equals(userId) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Check if token is refresh token
    public boolean isRefreshToken(String token) {
        try {
            return "refreshToken".equals(extractTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }

    // Check if token is access token
    public boolean isAccessToken(String token) {
        try {
            return "accessToken".equals(extractTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }
}