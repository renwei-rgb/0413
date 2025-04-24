package com.tss.atm.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.auth.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User getByEmployeeId(String employeeId);
    List<User> getByDepartment(String department);
} 