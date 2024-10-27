package com.anas.collaborative.collaborative_app.repository;

import com.anas.collaborative.collaborative_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String roleUser);
}
