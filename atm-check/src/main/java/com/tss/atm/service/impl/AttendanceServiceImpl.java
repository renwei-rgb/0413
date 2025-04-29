package com.tss.atm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.common.entity.User;
import com.tss.atm.entity.Attendance;
import com.tss.atm.mapper.AttendanceMapper;
import com.tss.atm.service.AttendanceService;
import com.tss.atm.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {
//
//    @Autowired
//    private UserMapper userMapper;

    private static final LocalTime WORK_START_TIME = LocalTime.of(9, 0);
    private static final LocalTime WORK_END_TIME = LocalTime.of(18, 0);
    
    @Override
    public boolean checkIn(String userId, LocalDateTime checkInTime) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance != null) {
            return false; // 已经打过卡
        }
        
        attendance = new Attendance();
        attendance.setEmployeeId(userId);
        attendance.setCheckInTime(checkInTime);
        attendance.setStatus(checkInTime.toLocalTime().isAfter(WORK_START_TIME) ? "late" : "normal");
        
        return save(attendance);
    }
    
    @Override
    public boolean checkOut(String employeeId, LocalDateTime checkOutTime) {
        Attendance attendance = getTodayAttendance(employeeId);
        if (attendance == null || attendance.getCheckOutTime() != null) {
            return false; // 没有上班打卡记录或已经打过下班卡
        }
        
        attendance.setCheckOutTime(checkOutTime);
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
               .ge(Attendance::getCheckInTime, today.atStartOfDay())
               .lt(Attendance::getCheckInTime, today.plusDays(1).atStartOfDay());
        return getOne(wrapper);
    }
    
    @Override
    public List<Attendance> getAttendanceByDateRange(String employeeId, LocalDate startTime, LocalDate endTime) {
        LambdaQueryWrapper<Attendance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attendance::getEmployeeId, employeeId)
               .ge(Attendance::getCheckInTime, startTime)
               .lt(Attendance::getCheckInTime, endTime)
               .orderByAsc(Attendance::getCheckInTime);
        return list(wrapper);
    }
    
//    @Override
//    public List<User> getByDepartment(String department) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getDepartment, department);
//        return userMapper.selectList(wrapper);
//    }
} 