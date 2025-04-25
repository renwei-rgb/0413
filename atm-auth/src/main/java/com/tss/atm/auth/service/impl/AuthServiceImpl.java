package com.tss.atm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.tss.atm.auth.dto.LoginRequest;
import com.tss.atm.auth.dto.LoginResponse;
import com.tss.atm.auth.entity.User;
import com.tss.atm.auth.service.AuthService;
import com.tss.atm.auth.service.UserService;
import com.tss.atm.common.config.UserStatus;
import com.tss.atm.common.exception.BusinessException;
import com.tss.atm.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, User> redisTemplate;
    private static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60; // 24小时过期

    public AuthServiceImpl(UserService userService, RedisTemplate<String, User> redisTemplate) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.redisTemplate = redisTemplate;
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
        if (user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new BusinessException("用户已被禁用");
        }

        // 生成token
        String token = JwtUtils.generateToken(user.getUsername());
        
        // 存储到Redis，设置24小时过期
        redisTemplate.opsForValue().set(
            "TOKEN:" + token, 
            user, 
            TOKEN_EXPIRE_TIME, 
            TimeUnit.SECONDS
        );

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

    private <T> void checkFieldUnique(SFunction<User, T> getField, T value, String fieldName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(getField, value);
        if (userService.getOne(wrapper) != null) {
            throw new BusinessException(fieldName + "已存在");
        }
    }

    @Override
    public User register(User user) {
        // 检查唯一性
        checkFieldUnique(User::getEmployeeId, user.getEmployeeId(), "工号");
        checkFieldUnique(User::getEmail, user.getEmail(), "邮箱");
        checkFieldUnique(User::getPhone, user.getPhone(), "手机号");

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 设置默认值
        return userService.save(user) ? user : null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("TOKEN:" + token);
    }

    @Override
    public User getCurrentUser(String token) {
        User user = redisTemplate.opsForValue().get("TOKEN:" + token);
        if (user == null) {
            throw new BusinessException("用户未登录或登录已过期");
        }
        return user;
    }
} 