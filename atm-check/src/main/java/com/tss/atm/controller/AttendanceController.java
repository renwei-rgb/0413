package com.tss.atm.controller;

import com.tss.atm.common.result.Result;
import com.tss.atm.entity.Attendance;
import com.tss.atm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    @Autowired
    private DataSource dataSource;

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public Result<Boolean> checkIn(@RequestParam String userId) {
        return Result.success(attendanceService.checkIn(userId, LocalDateTime.now()));
    }
    
    @PostMapping("/check-out")
    public Result<Boolean> checkOut(@RequestParam String userId) {
        return Result.success(attendanceService.checkOut(userId, LocalDateTime.now()));
    }
    
    @GetMapping("/today")
    public Result<Attendance> getTodayAttendance(@RequestParam String userId) {
        return Result.success(attendanceService.getTodayAttendance(userId));
    }
////
//    @Component
//    public class TestPrint {
//
//        @Autowired
//        private DatasourceProperties properties;
//
//        @PostConstruct
//        public void init() {
//            System.out.println("数据库地址: " + properties.getUrl());
//        }
//
//
//        @Component
//        public class TestPrint1 {
//
//            HikariDataSource hikari = (HikariDataSource) dataSource;
//
//            @PostConstruct
//            public void init() {
//                System.out.println("数据库地址: " + hikari.getJdbcUrl());
//            }
//        }
//
//    }
//    @Component
//    public class TestPrint1 {
//
//        @Autowired
//        private DatasourceProperties properties;
//
//        @PostConstruct
//        public void init() {
//            System.out.println("数据库地址: " + properties.getUrl());
//        }
//    }
//    @PostConstruct
//    public void logDatabaseUrl() {
//        if (dataSource instanceof HikariDataSource) {
//            HikariDataSource hikari = (HikariDataSource) dataSource;
//            System.out.println("数据库地址 (AttendanceController): " + hikari.getJdbcUrl());
//
//        } else {
//            System.out.println("数据库地址 (AttendanceController): 无法确定数据源类型以获取 URL.");
//        }
////        return hikari.getJdbcUrl();
//    }
} 