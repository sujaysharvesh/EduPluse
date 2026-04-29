package com.example.Edupulse.user.dto;


import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
