package com.example.Edupulse.user;


import com.example.Edupulse.common.ApiResponse;
import com.example.Edupulse.user.dto.CreateUserRequest;
import com.example.Edupulse.user.dto.LoginRequest;
import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {



    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> currentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody CreateUserRequest createUserRequest
    ) {

        String response = userService.registerUser(createUserRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("register successful"));
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