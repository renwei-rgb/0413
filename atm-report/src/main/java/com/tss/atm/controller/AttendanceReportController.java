package com.tss.atm.controller;

import com.tss.atm.common.Result;
import com.tss.atm.entity.AttendanceReport;
import com.tss.atm.service.AttendanceReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance-report")
@RequiredArgsConstructor
public class AttendanceReportController {
    
    private final AttendanceReportService attendanceReportService;
    
    @PostMapping("/generate")
    public Result<AttendanceReport> generateMonthlyReport(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
        return Result.success(attendanceReportService.generateMonthlyReport(employeeId, reportDate));
    }
    
    @GetMapping("/monthly")
    public Result<AttendanceReport> getMonthlyReport(
            @RequestParam String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
        return Result.success(attendanceReportService.getMonthlyReport(employeeId, reportDate));
    }
    
    @GetMapping("/department")
    public Result<List<AttendanceReport>> getDepartmentMonthlyReport(
            @RequestParam String department,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
        return Result.success(attendanceReportService.getDepartmentMonthlyReport(department, reportDate));
    }
} 