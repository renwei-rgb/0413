package com.tss.atm.user.controller;


import com.tss.atm.user.entity.User;
import com.tss.atm.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // 查询所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userMapper.selectList(null);  // 查询全部
    }

    // 根据 ID 查询用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userMapper.selectById(id);
    }
}
