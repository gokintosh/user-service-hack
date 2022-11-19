package com.gokul.userhackservice.service;

import com.gokul.userhackservice.model.AppUser;
import com.gokul.userhackservice.model.ERole;
import com.gokul.userhackservice.model.Role;
import com.gokul.userhackservice.repository.RoleRepository;
import com.gokul.userhackservice.repository.UserRepository;
import com.gokul.userhackservice.request.LoginRequest;
import com.gokul.userhackservice.request.SignUpRequest;
import com.gokul.userhackservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    public String registerUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists try signing in or user another email");
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username already exists try signing in or use another username");
        }

        AppUser user = new AppUser();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setGmnia(signUpRequest.getGmnia());
        user.setAddress(signUpRequest.getAddress());
        user.setDeviceId(new Random().nextLong());



        Set<String> roles = signUpRequest.getRole();
        Set<Role> userRoles = new HashSet<>();

        if (roles.isEmpty()) {
            Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found!!"));
            userRoles.add(role);
        } else {
            roles.forEach(roleSet -> {
                switch (roleSet) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(()
                                -> new RuntimeException("role not found!"));
                        userRoles.add(adminRole);
                        break;

                    case "mod":
                        Role moderatorRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() ->
                                new RuntimeException("Role not found!!")
                        );
                        userRoles.add(moderatorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                                new RuntimeException("Role not found!!")
                        );
                        userRoles.add(userRole);
                }
            });
        }


        user.setRoles(userRoles);
        AppUser user1 = userRepository.save(user);


        return user1.getDeviceId().toString();
    }

    public String loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return jwt;


    }
}
