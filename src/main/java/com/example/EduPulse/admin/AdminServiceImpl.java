//package com.example.Edupulse.admin;
//
//import com.example.Edupulse.admin.dto.AdminCredentials;
//import com.example.Edupulse.admin.dto.CreateAdminRequest;
//import com.example.Edupulse.admin.dto.CreateAdminResponse;
//import com.example.Edupulse.config.PasswordConfig;
//import com.example.Edupulse.school.School;
//import com.example.Edupulse.school.SchoolRepo;
//import com.example.Edupulse.utils.AdminUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    private final SchoolRepo schoolRepo;
//    private final AdminUtils utils;
//    private final AdminRepo adminRepo;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public AdminServiceImpl(SchoolRepo schoolRepo, AdminUtils utils, AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
//        this.schoolRepo = schoolRepo;
//        this.utils = utils;
//        this.adminRepo = adminRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public CreateAdminResponse createAdmin(CreateAdminRequest request) {
//        School school = schoolRepo.findById(request.getSchoolId()).orElseThrow(() ->
//          new RuntimeException()
//        );
//
//        AdminCredentials credentials = utils.generateAdminCredentials(school);
//
//        String hashPassword = passwordEncoder.encode(credentials.getPassword());
//
////        Admin admin = adminRepo.registerAdmin(school.getId(), credentials.getUsername(), hashPassword);
//
//        return CreateAdminResponse.builder()
//                .adminName(credentials.getUsername())
//                .adminPassword(credentials.getPassword())
//                .build();
//
//    }
//
//    @Override
//    public void logiAdmin(AdminCredentials adminCredentials) {
//        Optional<Admin> admin = adminRepo.findByAdminName(adminCredentials.getUsername());
//
//        if(!admin.isPresent()) {
//            throw new RuntimeException();
//        }
//
//        if(adminCredentials.getPassword().equals(passwordEncoder.equals(adminCredentials.getPassword()))) {
//            throw new RuntimeException();
//        }
//
//        return;
//
//
//    }
//
//
//}
