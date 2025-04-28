package com.tss.atm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tss.atm.entity.AttendanceReport;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceReportService extends IService<AttendanceReport> {
    // 生成月度考勤报告
    AttendanceReport generateMonthlyReport(String employeeId, LocalDate reportDate);
    
    // 获取指定月份的考勤报告
    AttendanceReport getMonthlyReport(String employeeId, LocalDate reportDate);
    
    // 获取部门月度考勤报告
    List<AttendanceReport> getDepartmentMonthlyReport(String department, LocalDate reportDate);
} 