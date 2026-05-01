package com.example.EduPulse.user.handler;

import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.mapper.SuperAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuperAdminHandler implements UserHandler {

    private final SuperAdminMapper superAdminMapper;

    @Override
    public String getRoleName() {
        return "SUPER_ADMIN";
    }

    @Override
    public void handle(User user, CreateUserRequest createUserRequest) {
    }

    @Override
    public Object buildResponse(User user) {
        return superAdminMapper.toDto(user);
    }

}
