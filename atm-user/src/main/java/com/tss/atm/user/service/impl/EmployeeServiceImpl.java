package com.tss.atm.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.auth.mapper.UserMapper;
import com.tss.atm.user.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<UserMapper, Users> implements EmployeeService {



    @Autowired
    private UserMapper userMapper;

    @Override
    public Users getByEmployeeId(String employeeId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getId, employeeId);
        return getOne(wrapper);
    }
    
    @Override
    public boolean validateLogin(String userId, String password) {
        Users user = getByEmployeeId(userId);
        if (user == null) {
            return false;
        }
        // 这里应该使用加密后的密码进行比对
//        return user.status() == 1; // 在职状态
        return true;
    }

    @Override
    public List<Users> getByDepartment(String department) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getDepartment, department);
        return userMapper.selectList(wrapper);
    }
} 