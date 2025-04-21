package com.tss.atm.controller;

import com.tss.atm.common.Result;
import com.tss.atm.entity.User;
import com.tss.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }
    
    @PostMapping
    public Result<Boolean> createUser(@RequestBody User user) {
        return Result.success(userService.save(user));
    }
    
    @PutMapping
    public Result<Boolean> updateUser(@RequestBody User user) {
        return Result.success(userService.updateById(user));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        return Result.success(userService.removeById(id));
    }
} 