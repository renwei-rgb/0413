package com.tss.atm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.dto.LoginRequest;
import com.tss.atm.common.dto.LoginResponse;

import java.util.List;

public interface UserService extends IService<User> {
    // 用户注册
    boolean register(UserRegisterDTO dto);

    // 通过用户名查用户
    User getByUsername(String username);
    // 通过工号查用户
    User getByEmployeeId(String employeeId);

    // 通过部门查用户
    List<User> getByDepartment(String department);

    // 修改密码
    boolean updatePassword(Long userId, String encodedPwd);

    // 用户登录
    LoginResponse login(LoginRequest request);
    
    // 用户登出
    void logout(String token);
    
    // 获取当前用户
    User getCurrentUser(String token);
    
    // 修改密码
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    // 获取用户角色
    List<String> getUserRoles(Long userId);

    // 获取用户权限
    List<String> getUserPermissions(Long userId);

    // 其他用户信息相关业务
}