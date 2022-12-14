package com.gokul.userhackservice.security.service;


import com.gokul.userhackservice.model.AppUser;
import com.gokul.userhackservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user=userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found with username : "+username));

        return UserDetailsImpl.build(user);
    }
}
