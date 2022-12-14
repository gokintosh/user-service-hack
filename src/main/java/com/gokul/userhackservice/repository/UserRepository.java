package com.gokul.userhackservice.repository;


import com.gokul.userhackservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser>findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    AppUser findByDeviceId(Long deviceId);

}
