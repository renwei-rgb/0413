package com.tss.atm.auth.service;

import com.tss.atm.auth.dto.LoginResult;
import com.tss.atm.common.dto.UserRegisterDTO;
import com.tss.atm.common.entity.User;
import com.tss.atm.common.result.Result;

import java.util.List;

public interface AuthService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果（含token、用户信息、权限等）
     */

    LoginResult login(String username, String password);

    /**
     * 用户注册
     * @param dto 注册参数
     * @return 是否注册成功
     */
    Result<Boolean> register(UserRegisterDTO dto);

    /**
     * 校验token
     * @param token token字符串
     * @return 是否有效
     */
    boolean validateToken(String token);

    /**
     * 生成token
     * @param user 用户信息
     * @return token字符串
     */
    String generateToken(User user);

    /**
     * 用户登出
     * @param token token字符串
     */
    void logout(String token);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    Result<Boolean> changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 获取用户权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<String> getUserRoles(Long userId);

    Object getCurrentUser(String token);

}