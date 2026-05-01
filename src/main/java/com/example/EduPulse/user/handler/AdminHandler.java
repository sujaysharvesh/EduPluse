package com.example.EduPulse.user.handler;

import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import com.example.EduPulse.user.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminHandler implements UserHandler {

    private final AdminMapper adminMapper;

    @Override
    public String getRoleName() {
        return "ADMIN";
    }

    @Override
    public void handle(User user, CreateUserRequest createUserRequest) {
    }

    @Override
    public Object buildResponse(User user) {
        return adminMapper.toDto(user);
    }
}
