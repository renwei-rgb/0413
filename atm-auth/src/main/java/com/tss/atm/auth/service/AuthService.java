package com.tss.atm.auth.service;

import com.tss.atm.auth.dto.LoginRequest;
import com.tss.atm.auth.dto.LoginResponse;
import com.tss.atm.auth.entity.User;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    User register(User user);
    void logout(String token);
    User getCurrentUser(String token);
} 