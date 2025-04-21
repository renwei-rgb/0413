package com.tss.atm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.entity.Attendance;

import java.time.LocalDateTime;

public interface AttendanceService extends IService<Attendance> {
    // 上班打卡
    boolean checkIn(String employeeId, LocalDateTime checkInTime);
    
    // 下班打卡
    boolean checkOut(String employeeId, LocalDateTime checkOutTime);
    
    // 获取当天的考勤记录
    Attendance getTodayAttendance(String employeeId);
} 