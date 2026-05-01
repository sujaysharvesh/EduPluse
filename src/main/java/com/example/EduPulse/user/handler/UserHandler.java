package com.example.EduPulse.user.handler;


import com.example.EduPulse.user.User;
import com.example.EduPulse.user.dto.request.CreateUserRequest;
import org.springframework.stereotype.Component;


@Component
public interface UserHandler {

    String getRoleName();

    void handle(User user, CreateUserRequest createUserRequest);

    Object buildResponse(User user);
}
