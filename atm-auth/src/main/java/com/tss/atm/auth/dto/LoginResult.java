package com.tss.atm.auth.dto;


import lombok.Data;
import java.util.List;

@Data
public class LoginResult {
    private String token;           // 登录成功后的JWT或Session Token
    private Long userId;            // 用户ID
    private String username;        // 用户名
    private String realName;        // 真实姓名
    private List<String> roles;     // 角色列表
    private List<String> permissions; // 权限列表
    private String avatar;          // 头像URL（可选）
    private String department;      // 部门（可选）
    // 你可以根据业务需要添加更多字段
}