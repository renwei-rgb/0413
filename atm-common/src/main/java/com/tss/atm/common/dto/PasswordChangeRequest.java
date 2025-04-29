package com.tss.atm.common.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
} 