package com.example.Spring.Security.repositories;

import com.example.Spring.Security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role save (Role role);
    Optional<Role> findByRoleName(String roleName);
}
