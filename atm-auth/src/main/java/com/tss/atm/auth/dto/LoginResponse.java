package com.tss.atm.auth.dto;

import lombok.Data;
import lombok.Builder;

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