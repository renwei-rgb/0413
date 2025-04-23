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
    private String employeeId;      // 工号
    private String name;            // 姓名
    private String department;      // 部门
    private String position;        // 职位
    private String status;          // 状态：active-在职，inactive-离职
} 