package com.tss.atm.auth.controller;

import com.tss.atm.common.result.Result;
import com.tss.atm.auth.entity.User;
import com.tss.atm.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserFeignClient userFeignClient;
    
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody User user) {
        return userFeignClient.createUser(user);
    }
    
    @GetMapping("/user/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        return userFeignClient.getUserById(id);
    }
    
    @PutMapping("/user")
    public Result<Boolean> updateUserInfo(@RequestBody User user) {
        return userFeignClient.updateUser(user);
    }
} 