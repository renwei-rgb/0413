package com.tss.atm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_attendance")
public class Attendance {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String employeeId;      // 工号
    private LocalDateTime checkInTime;  // 上班打卡时间
    private LocalDateTime checkOutTime; // 下班打卡时间
    private String status;          // 状态：normal-正常，late-迟到，early-早退，absent-缺勤
    private String remark;          // 备注
}