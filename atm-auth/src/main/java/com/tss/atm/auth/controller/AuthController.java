package com.tss.atm.auth.controller;

import com.tss.atm.auth.dto.LoginRequest;
import com.tss.atm.auth.dto.LoginResult;
import com.tss.atm.auth.service.AuthService;
import com.tss.atm.common.dto.UserRegisterDTO;
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
    public Result<LoginResult> login(@RequestBody LoginRequest request) {
        return Result.success(authService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/register")
    public Result<Boolean> registerUser(@RequestBody UserRegisterDTO dto) {
        try {
            // 直接返回 service 层的 Result<Boolean>
            return authService.register(dto);
        } catch (RuntimeException e) {
            // 直接返回错误结果
            return Result.error(500,e.getMessage());
        } catch (Exception e) {
            // 系统异常
            return Result.error(500,"注册失败：" + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success();
    }

    @GetMapping("/validate")
    public Result<Boolean> validateToken(@RequestParam String token) {
        boolean valid = authService.validateToken(token);
        return Result.success(valid);
    }

    @PostMapping("/change-password")
    public Result<Boolean> changePassword(@RequestParam Long userId,
                                        @RequestParam String oldPassword,
                                        @RequestParam String newPassword) {
        Result<Boolean> success = authService.changePassword(userId, oldPassword, newPassword);
        return success;
    }
} 