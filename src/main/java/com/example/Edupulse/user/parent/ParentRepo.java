package com.example.Edupulse.user.parent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParentRepo extends JpaRepository<Parent, UUID> {

    Optional<Parent> findByUserId(UUID userId);

}
