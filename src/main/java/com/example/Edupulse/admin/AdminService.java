package com.example.Edupulse.admin;

import com.example.Edupulse.admin.dto.AdminCredentials;
import com.example.Edupulse.admin.dto.CreateAdminRequest;
import com.example.Edupulse.admin.dto.CreateAdminResponse;
import org.springframework.stereotype.Service;


@Service
public interface AdminService {

    CreateAdminResponse createAdmin(CreateAdminRequest request);

    void logiAdmin(AdminCredentials adminCredentials);


    /**
     needed to have function for updated admin password;
     */

}
