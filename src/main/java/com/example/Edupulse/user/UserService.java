package com.example.Edupulse.user;

import com.example.Edupulse.user.dto.UserRequest;
import com.example.Edupulse.user.dto.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface UserService {

    String registerUser(UserRequest request);

    void updateUser(UUID userId, UserRequest userRequest);

    void login(LoginRequest loginRequest, HttpServletResponse response);

    void logout(HttpServletResponse response);

}
