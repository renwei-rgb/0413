package com.tss.atm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.entity.Attendance;
import com.tss.atm.mapper.AttendanceMapper;
import com.tss.atm.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    private static final LocalTime WORK_START_TIME = LocalTime.of(9, 0);
    private static final LocalTime WORK_END_TIME = LocalTime.of(18, 0);
    
    @Override
    public boolean checkIn(String employeeId, LocalDateTime checkInTime) {
        Attendance attendance = getTodayAttendance(employeeId);
        if (attendance != null) {
            return false; // 已经打过卡
        }
        
        attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setCheckIn(checkInTime);
        attendance.setStatus(checkInTime.toLocalTime().isAfter(WORK_START_TIME) ? "late" : "normal");
        
        return save(attendance);
    }
    
    @Override
    public boolean checkOut(String employeeId, LocalDateTime checkOutTime) {
        Attendance attendance = getTodayAttendance(employeeId);
        if (attendance == null || attendance.getCheckOut() != null) {
            return false; // 没有上班打卡记录或已经打过下班卡
        }
        
        attendance.setCheckOut(checkOutTime);
        if (checkOutTime.toLocalTime().isBefore(WORK_END_TIME)) {
            attendance.setStatus("early");
        }
        
        return updateById(attendance);
    }
    
    @Override
    public Attendance getTodayAttendance(String employeeId) {
        LocalDate today = LocalDate.now();
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attendance::getEmployeeId, employeeId)
               .ge(Attendance::getCheckIn, today.atStartOfDay())
               .lt(Attendance::getCheckIn, today.plusDays(1).atStartOfDay());
        return getOne(wrapper);
    }
} 