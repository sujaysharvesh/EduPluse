package com.example.Edupulse.security;

import com.example.Edupulse.user.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

    public JwtUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof JwtUser)) {
            throw new RuntimeException("No authenticated user found");
        }
        return (JwtUser) auth.getPrincipal();
    }

    public String getCurrentUserSchoolId() {
        return getCurrentUser().getSchoolId();
    }

    public String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }

    public boolean isSuperAdmin() {
        return "SUPER_ADMIN".equals(getCurrentUserRole());
    }
}