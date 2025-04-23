package com.tss.atm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.user.entity.Employee;

public interface EmployeeService extends IService<Employee> {
    // 根据工号查询员工
    Employee getByEmployeeId(String employeeId);
    
    // 验证员工登录
    boolean validateLogin(String employeeId, String password);
} 