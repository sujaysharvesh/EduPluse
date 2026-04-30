package com.example.Edupulse.user;


import com.example.Edupulse.common.ApiResponse;
import com.example.Edupulse.user.dto.request.CreateUserRequest;
import com.example.Edupulse.user.dto.request.UserRequest;
import com.example.Edupulse.user.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {



    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Object>> currentUser() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Object profile = userService.getProfile(jwtUser.getId(), jwtUser.getRole());

        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody CreateUserRequest createUserRequest
    ) {

        userService.registerUser(createUserRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("register successful"));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable UUID userId,
            @RequestBody UserRequest request
    ) {
        userService.updateUser(userId, request);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Valid @RequestBody LoginRequest request, HttpServletResponse response) {

        userService.login(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Login successful"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("logout successful"));

    }



    }