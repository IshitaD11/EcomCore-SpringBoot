package org.example.userauthenticationservice.repositories;

import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.models.RoleTypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleTypeName(RoleTypeName roleTypeName);
}
