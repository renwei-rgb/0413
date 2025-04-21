package com.tss.atm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_employee")
public class Employee {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String employeeId;  // 工号
    private String name;        // 姓名
    private String department;  // 部门
    private String position;    // 职位
    private String email;
    private String phone;
    private Integer status;     // 状态：0-离职，1-在职
    private String role;        // 角色：admin-管理员，employee-普通员工
} 