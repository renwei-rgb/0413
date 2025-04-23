package com.tss.atm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tss.atm.user.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
} 