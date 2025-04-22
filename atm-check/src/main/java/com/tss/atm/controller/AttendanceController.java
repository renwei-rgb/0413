package com.tss.atm.controller;

import com.tss.atm.common.Result;
import com.tss.atm.entity.Attendance;
import com.tss.atm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @PostMapping("/check-in")
    public Result<Boolean> checkIn(@RequestParam String employeeId) {
        return Result.success(attendanceService.checkIn(employeeId, LocalDateTime.now()));
    }
    
    @PostMapping("/check-out")
    public Result<Boolean> checkOut(@RequestParam String employeeId) {
        return Result.success(attendanceService.checkOut(employeeId, LocalDateTime.now()));
    }
    
    @GetMapping("/today")
    public Result<Attendance> getTodayAttendance(@RequestParam String employeeId) {
        return Result.success(attendanceService.getTodayAttendance(employeeId));
    }
    //test
} 