package com.example.EduPulse.admin;

import com.example.EduPulse.admin.dto.AdminCredentials;
import com.example.EduPulse.admin.dto.CreateAdminRequest;
import com.example.EduPulse.admin.dto.CreateAdminResponse;
import org.springframework.stereotype.Service;


@Service
public interface AdminService {

    CreateAdminResponse createAdmin(CreateAdminRequest request);

    void logiAdmin(AdminCredentials adminCredentials);


    /**
     needed to have function for updated admin password;
     */

}
