package com.tss.atm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.auth.entity.User;

import java.util.List;

public interface EmployeeService extends IService<User> {
    // 根据工号查询员工
    User getByEmployeeId(String employeeId);
    
    // 验证员工登录
    boolean validateLogin(String employeeId, String password);

    List<User> getByDepartment(String department);

} 