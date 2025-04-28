package com.tss.atm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@TableName("sys_attendance_report")
public class AttendanceReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String employeeId;      // 工号
    private LocalDate reportDate;   // 统计日期
    private Integer workDays;       // 应出勤天数
    private Integer actualDays;     // 实际出勤天数
    private Integer lateTimes;      // 迟到次数
    private Integer earlyTimes;     // 早退次数
    private Integer absentDays;     // 缺勤天数
    private Duration totalHours;    // 总工作时长
    private String remark;          // 备注
} 