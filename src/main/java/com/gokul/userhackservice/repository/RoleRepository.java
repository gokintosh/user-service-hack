package com.gokul.userhackservice.repository;


import com.gokul.userhackservice.model.ERole;
import com.gokul.userhackservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role>findByName(ERole name);
}
