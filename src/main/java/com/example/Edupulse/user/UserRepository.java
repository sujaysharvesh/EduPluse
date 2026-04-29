package com.example.Edupulse.user;

import com.example.Edupulse.common.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);

    // Used by CustomUserDetailsService — loads by username (which is what JWT stores)
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Find all admins belonging to a specific school
    List<User> findBySchoolIdAndRole(UUID schoolId, UserRole role);



}
