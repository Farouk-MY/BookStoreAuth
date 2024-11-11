package com.Bee.Store.BeeStore.controller;

import com.Bee.Store.BeeStore.dto.SignupRequest;
import com.Bee.Store.BeeStore.dto.LoginRequest;
import com.Bee.Store.BeeStore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
