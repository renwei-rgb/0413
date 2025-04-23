package com.tss.atm.controller;

import com.tss.DatasourceProperties;
import com.tss.atm.common.*;
import com.tss.atm.common.result.Result;
import com.tss.atm.entity.Attendance;
import com.tss.atm.service.AttendanceService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    @Autowired
    private DataSource dataSource;

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

    @Component
    public class TestPrint {

        @Autowired
        private DatasourceProperties properties;

        @PostConstruct
        public void init() {
            System.out.println("数据库地址: " + properties.getUrl());
        }

        public class TestPrint1 {

            HikariDataSource hikari = (HikariDataSource) dataSource;

            @PostConstruct
            public void init() {
                System.out.println("数据库地址: " + hikari.getJdbcUrl());
            }


        }

    }
} 