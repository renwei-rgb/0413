package com.tss.atm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tss.atm.dto.LoginResult;
import com.tss.atm.dto.UserRegisterDTO;
import com.tss.atm.entity.LoginLog;
import com.tss.atm.entity.User;
import com.tss.atm.repository.LoginLogRepository;
import com.tss.atm.repository.UserRepository;
import com.tss.atm.service.AuthService;
import com.tss.atm.util.JwtUtil;
import com.tss.atm.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResult login(String username, String password) {
        User user = userRepository.selectOne(
            new QueryWrapper<User>().eq("username", username)
        );

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return LoginResult.fail("用户名或密码错误");
        }

        if (!"active".equals(user.getStatus())) {
            return LoginResult.fail("账号已被禁用");
        }

        String token = generateToken(user);
        return LoginResult.success(token, user);
    }

    @Override
    @Transactional
    public Result<Boolean> register(UserRegisterDTO dto) {
        // 检查用户名是否已存在
        if (userRepository.selectCount(
            new QueryWrapper<User>().eq("username", dto.getUsername())
        ) > 0) {
            return Result.fail("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setDepartment(dto.getDepartment());
        user.setEmployeeId(dto.getEmployeeId());
        user.setRole("USER");
        user.setStatus("active");

        userRepository.insert(user);
        return Result.success(true);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    @Override
    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    @Override
    public void logout(String token) {
        // 记录登出日志
        LoginLog log = LoginLog.builder()
            .username(jwtUtil.getUsernameFromToken(token))
            .status("logout")
            .timestamp(LocalDateTime.now())
            .build();
        loginLogRepository.insert(log);
    }

    @Override
    public Result<Boolean> changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return Result.fail("原密码错误");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.updateById(user);
        return Result.success(true);
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        // TODO: 实现权限获取逻辑
        return new ArrayList<>();
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole());
        return roles;
    }

    @Override
    public Object getCurrentUser(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return userRepository.selectOne(
            new QueryWrapper<User>().eq("username", username)
        );
    }
} 