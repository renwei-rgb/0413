package com.tss.atm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.auth.entity.User;
import com.tss.atm.entity.Attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService extends IService<Attendance> {
    // 上班打卡
    boolean checkIn(String employeeId, LocalDateTime checkInTime);
    
    // 下班打卡
    boolean checkOut(String employeeId, LocalDateTime checkOutTime);
    
    // 获取当天的考勤记录
    Attendance getTodayAttendance(String employeeId);
    
    // 获取指定日期范围内的考勤记录
    List<Attendance> getAttendanceByDateRange(String employeeId, LocalDate startTime, LocalDate endTime);
    
    // 根据部门查询所有员工
    List<User> getByDepartment(String department);
} 