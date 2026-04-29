package com.example.Edupulse.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepo extends JpaRepository<Admin, UUID> {


//    Admin registerAdmin(UUID schoolId, String adminName, String adminPassword);
//    Optional<Admin> findByAdminName(String adminName);
////    Admin updatedAdminPassword(String adminName, String password);
//    void deleteAdmin(UUID adminId);

}
