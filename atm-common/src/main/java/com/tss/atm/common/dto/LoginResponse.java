package com.tss.atm.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private String employeeId;
    private String role;
    private String name;
    private String department;
} 