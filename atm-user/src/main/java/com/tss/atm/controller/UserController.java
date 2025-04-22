package com.tss.atm.controller;

import com.tss.atm.common.result.Result;
import com.tss.atm.entity.User;
import com.tss.atm.user.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper; // 直接注入 UserMapper

    @GetMapping("/{id}")
    public Result<List<com.tss.atm.common.entity.User>> getUserById(@PathVariable Long id) {
        return Result.success(userMapper.selectList(null));
    }
    
//    @PostMapping
//    public Result<Boolean> createUser(@RequestBody User user) {
//        return Result.success(userMapper.save(user));
//    }
//
//    @PutMapping
//    public Result<Boolean> updateUser(@RequestBody User user) {
//        return Result.success(userMapper.updateById(user));
//    }
//
//    @DeleteMapping("/{id}")
//    public Result<Boolean> deleteUser(@PathVariable Long id) {
//        return Result.success(userMapper.removeById(id));
//    }
} 