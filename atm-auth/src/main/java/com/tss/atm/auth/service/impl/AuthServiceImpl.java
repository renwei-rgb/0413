package com.tss.atm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tss.atm.auth.dto.LoginRequest;
import com.tss.atm.auth.dto.LoginResponse;
import com.tss.atm.auth.entity.User;
import com.tss.atm.auth.service.AuthService;
import com.tss.atm.auth.service.UserService;
import com.tss.atm.common.exception.BusinessException;
import com.tss.atm.common.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, User> tokenStorage = new HashMap<>();

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查找用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User user = userService.getOne(wrapper);

        // 验证用户存在且密码正确
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("用户已被禁用");
        }

        // 生成token
        String token = JwtUtils.generateToken(user.getUsername());
        tokenStorage.put(token, user);

        // 返回登录响应
        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .employeeId(user.getEmployeeId())
                .role(user.getRole())
                .name(user.getName())
                .department(user.getDepartment())
                .build();
    }

    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        if (userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername())) != null) {
            throw new BusinessException("用户名已存在");
        }

        // 检查工号是否已存在
        if (userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmployeeId, user.getEmployeeId())) != null) {
            throw new BusinessException("工号已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // 设置默认值
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        // 保存用户
        userService.save(user);
        return user;
    }

    @Override
    public void logout(String token) {
        tokenStorage.remove(token);
    }

    @Override
    public User getCurrentUser(String token) {
        User user = tokenStorage.get(token);
        if (user == null) {
            throw new BusinessException("用户未登录或登录已过期");
        }
        return user;
    }
} 