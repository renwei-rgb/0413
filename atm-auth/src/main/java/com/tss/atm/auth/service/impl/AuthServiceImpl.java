package com.tss.atm.auth.service.impl;

import com.tss.atm.auth.dto.LoginResult;
import com.tss.atm.auth.service.AuthService;
import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.result.Result;
import com.tss.atm.common.utils.JwtUtils;
import com.tss.atm.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final UserFeignClient userFeignClient;
    private final RedisTemplate<String, User> redisTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserFeignClient userFeignClient, RedisTemplate<String, User> redisTemplate) {
        this.userFeignClient = userFeignClient;
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 用户登录
     */
    @Override
    public LoginResult login(String username, String password) {
        Result<User> userResult = userFeignClient.getUserByUsername(username);
        User user = userResult.getData();
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        String token = JwtUtils.generateToken(user.getUsername());
        Result<List<String>> rolesResult = userFeignClient.getUserRoles(user.getId());
        Result<List<String>> permissionsResult = userFeignClient.getUserPermissions(user.getId());

        LoginResult result = new LoginResult();
        result.setToken(token);
        result.setUserId(user.getId());
        result.setUsername(user.getUsername());
        result.setRoles(rolesResult.getData());
        result.setPermissions(permissionsResult.getData());

        // 可选：缓存用户信息
        redisTemplate.opsForValue().set("login:user:" + user.getId(), user);

        return result;
    }

    /**
     * 用户注册
     * @return
     */
    @Override
    public Result<Boolean> register(UserRegisterDTO dto) {
        // 1. 校验用户名是否已存在
        Result<User> exist = userFeignClient.getUserByUsername(dto.getUsername());
        if (exist != null&& exist.getData() != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 2. 密码加密
        String encodedPwd = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPwd);

        // 3. 远程调用 user 服务注册
        return userFeignClient.registerUser(dto);

    }

    /**
     * 校验token
     */
    @Override
    public boolean validateToken(String token) {
        return JwtUtils.validateToken(token);
    }

    /**
     * 生成token
     */
    @Override
    public String generateToken(User user) {
        return JwtUtils.generateToken(user.getUsername());
    }

    /**
     * 用户登出
     */
    @Override
    public void logout(String token) {
        // 可选：将token加入黑名单，或删除Redis缓存
        String username = JwtUtils.getUsernameFromToken(token);
        redisTemplate.delete("login:user:" + username);
    }

    /**
     * 获取用户权限
     */
    @Override
    public List<String> getUserPermissions(Long userId) {
        return (List<String>) userFeignClient.getUserPermissions(userId);
    }

    /**
     * 获取用户角色
     */
    @Override
    public List<String> getUserRoles(Long userId) {
        return (List<String>) userFeignClient.getUserRoles(userId);
    }

    @Override
    public Object getCurrentUser(String token) {
        return null;
    }

    @Override
    public Result<Boolean> changePassword(Long userId, String oldPassword, String newPassword) {
        Result<User> user = userFeignClient.getUserById(userId);
        User userData = user.getData(); // 从 Result 中获取 User 对象

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword, userData.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        String encodedPwd = passwordEncoder.encode(newPassword);
        return userFeignClient.updatePassword(userId, encodedPwd);
    }
} 