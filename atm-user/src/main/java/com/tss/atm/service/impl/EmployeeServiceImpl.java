package com.tss.atm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.entity.Employee;
import com.tss.atm.mapper.EmployeeMapper;
import com.tss.atm.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    
    @Override
    public Employee getByEmployeeId(String employeeId) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getEmployeeId, employeeId);
        return getOne(wrapper);
    }
    
    @Override
    public boolean validateLogin(String employeeId, String password) {
        Employee employee = getByEmployeeId(employeeId);
        if (employee == null) {
            return false;
        }
        // 这里应该使用加密后的密码进行比对
        return employee.getStatus() == 1; // 在职状态
    }
} 