package com.example.Edupulse.user;


import com.example.Edupulse.user.dto.CreateUserRequest;
import com.example.Edupulse.user.dto.UserResponse;

public interface ServiceImp {

    UserResponse registerUser(CreateUserRequest request);

}
