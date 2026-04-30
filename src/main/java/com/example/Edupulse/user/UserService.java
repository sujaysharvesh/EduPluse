package com.example.Edupulse.user;

import com.example.Edupulse.user.dto.request.CreateUserRequest;
import com.example.Edupulse.user.dto.request.UserRequest;
import com.example.Edupulse.user.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface UserService {

    void registerUser(CreateUserRequest request);

    void updateUser(UUID userId, UserRequest userRequest);

    void login(LoginRequest loginRequest, HttpServletResponse response);

    void logout(HttpServletResponse response);

    Object getProfile(String userId, String role);

}
