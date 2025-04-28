package com.tss.atm.auth.controller;

import com.tss.atm.auth.dto.LoginRequest;
import com.tss.atm.auth.dto.LoginResponse;
import com.tss.atm.auth.entity.User;
import com.tss.atm.auth.service.AuthService;
import com.tss.atm.common.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return Result.success(authService.register(user));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success();
    }

    @GetMapping("/current-user")
    public Result<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return Result.success(authService.getCurrentUser(token));
    }
} 