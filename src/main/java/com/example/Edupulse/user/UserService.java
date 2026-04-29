package com.example.Edupulse.user;

import com.example.Edupulse.user.dto.CreateUserRequest;
import com.example.Edupulse.user.dto.LoginRequest;
import com.example.Edupulse.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    String registerUser(CreateUserRequest request);

    void login(LoginRequest loginRequest, HttpServletResponse response);

    void logout(HttpServletResponse response);

}
