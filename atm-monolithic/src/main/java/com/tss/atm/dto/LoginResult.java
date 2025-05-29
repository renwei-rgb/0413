package com.tss.atm.dto;

import com.tss.atm.entity.User;
import lombok.Data;

@Data
public class LoginResult {
    private boolean success;
    private String message;
    private String token;
    private User user;

    public static LoginResult success(String token, User user) {
        LoginResult result = new LoginResult();
        result.setSuccess(true);
        result.setToken(token);
        result.setUser(user);
        return result;
    }

    public static LoginResult fail(String message) {
        LoginResult result = new LoginResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
} 