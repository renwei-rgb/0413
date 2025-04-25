package com.tss.atm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface EmployeeService extends IService<Users> {
    // 根据工号查询员工
    Users getByEmployeeId(String employeeId);
    
    // 验证员工登录
    boolean validateLogin(String employeeId, String password);

    List<Users> getByDepartment(String department);

} 