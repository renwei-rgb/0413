package com.tss.atm.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("auth_login_log")
public class LoginLog {
    private Long id;
    private String username;
    private String ip;
    private String status;
    private LocalDateTime timestamp;
    private String role;
} 