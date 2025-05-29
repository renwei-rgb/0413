package com.tss.atm.controller;

import com.tss.atm.common.Result;
import com.tss.atm.dto.LoginResult;
import com.tss.atm.dto.UserRegisterDTO;
import com.tss.atm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResult login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }

    @PostMapping("/register")
    public Result<Boolean> register(@Valid @RequestBody UserRegisterDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success(true);
    }

    @PostMapping("/change-password")
    public Result<Boolean> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return authService.changePassword(userId, oldPassword, newPassword);
    }

    @GetMapping("/permissions")
    public Result<List<String>> getPermissions(@RequestParam Long userId) {
        return Result.success(authService.getUserPermissions(userId));
    }

    @GetMapping("/roles")
    public Result<List<String>> getRoles(@RequestParam Long userId) {
        return Result.success(authService.getUserRoles(userId));
    }

    @GetMapping("/current-user")
    public Result<Object> getCurrentUser(@RequestHeader("Authorization") String token) {
        return Result.success(authService.getCurrentUser(token));
    }
} 