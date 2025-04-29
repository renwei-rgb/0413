package com.tss.atm.common.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;      // 用户名
    private String password;      // 密码
    private String confirmPassword; // 确认密码（可选，前端校验用）
    private String email;         // 邮箱（可选）
    private String phone;         // 手机号（可选）
    private String employeeId;    // 工号（可选）
    private String department;    // 部门（可选）
    private String realName;      // 真实姓名（可选）
    // 你可以根据业务需要添加更多字段
}