package com.tss.atm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.common.dto.LoginRequest;
import com.tss.atm.common.dto.LoginResponse;
import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.user.mapper.UserMapper;
import com.tss.atm.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean register(UserRegisterDTO dto) {
        // 校验用户名唯一
        if (getByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return this.save(user);
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return this.getOne(wrapper);
    }

    @Override
    public User getByEmployeeId(String employeeId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("employee_id", employeeId);
        return this.getOne(wrapper);
    }

    @Override
    public List<User> getByDepartment(String department) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("department", department);
        return this.list(wrapper);
    }

    @Override
    public boolean updatePassword(Long userId, String encodedPwd) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(encodedPwd);
        return this.updateById(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public User getCurrentUser(String token) {
        return null;
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        // 实现获取用户角色的逻辑
        User user = this.getById(userId);
        if (user != null && user.getRole() != null) {
            return Collections.singletonList(user.getRole());
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        // 实现获取用户权限的逻辑
        // 如果没有权限系统，可以返回空列表
        return Collections.emptyList();
    }
}