package com.gokul.userhackservice.controller;



import com.gokul.userhackservice.request.LoginRequest;
import com.gokul.userhackservice.request.SignUpRequest;
import com.gokul.userhackservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){

        String deviceId=userService.registerUser(signUpRequest);

        return ResponseEntity.ok("Your device Id is: "+deviceId);
    }

    @PostMapping("/signin")
    public String loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);

    }
}
