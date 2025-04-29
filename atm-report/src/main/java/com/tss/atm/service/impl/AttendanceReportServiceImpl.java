package com.tss.atm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tss.atm.common.entity.User;
import com.tss.atm.entity.Attendance;
import com.tss.atm.entity.AttendanceReport;
import com.tss.atm.mapper.AttendanceReportMapper;
import com.tss.atm.service.AttendanceReportService;
import com.tss.atm.service.AttendanceService;
import com.tss.atm.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceReportServiceImpl extends ServiceImpl<AttendanceReportMapper, AttendanceReport> implements AttendanceReportService {
    
    private final AttendanceService attendanceService;
    private final UserService userService;
    
    public AttendanceReportServiceImpl(AttendanceService attendanceService, UserService userService) {
        this.attendanceService = attendanceService;
        this.userService = userService;
    }
    
    @Override
    public AttendanceReport generateMonthlyReport(String employeeId, LocalDate reportDate) {
        YearMonth yearMonth = YearMonth.from(reportDate);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 获取员工信息
        User user = userService.getByEmployeeId(employeeId);
        if (user == null) {
            return null;
        }
        
        // 创建报告
        AttendanceReport report = new AttendanceReport();
        report.setEmployeeId(employeeId);
        report.setReportDate(reportDate);
        
        // 计算工作日
        int workDays = calculateWorkDays(startDate, endDate);
        report.setWorkDays(workDays);
        
        // 获取考勤记录
        List<Attendance> attendances = attendanceService.getAttendanceByDateRange(employeeId, startDate, endDate);
        
        // 统计考勤数据
        int actualDays = 0;
        int lateTimes = 0;
        int earlyTimes = 0;
        int absentDays = 0;
        Duration totalHours = Duration.ZERO;
        
        for (Attendance attendance : attendances) {
            if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
                actualDays++;
                if ("late".equals(attendance.getStatus())) {
                    lateTimes++;
                }
                if ("early".equals(attendance.getStatus())) {
                    earlyTimes++;
                }
                totalHours = totalHours.plus(Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime()));
            }
        }
        
        absentDays = workDays - actualDays;
        
        report.setActualDays(actualDays);
        report.setLateTimes(lateTimes);
        report.setEarlyTimes(earlyTimes);
        report.setAbsentDays(absentDays);
        report.setTotalHours(totalHours);
        
        // 保存报告
        save(report);
        return report;
    }
    
    @Override
    public AttendanceReport getMonthlyReport(String employeeId, LocalDate reportDate) {
        LambdaQueryWrapper<AttendanceReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttendanceReport::getEmployeeId, employeeId)
               .eq(AttendanceReport::getReportDate, reportDate);
        return getOne(wrapper);
    }
    
    @Override
    public List<AttendanceReport> getDepartmentMonthlyReport(String department, LocalDate reportDate) {
        // 获取部门所有员工
        List<User> users = userService.getByDepartment(department);
        
        // 生成每个员工的月度报告
        return users.stream()
                .map(user -> generateMonthlyReport(user.getEmployeeId(), reportDate))
                .collect(Collectors.toList());
    }
    
    private int calculateWorkDays(LocalDate startDate, LocalDate endDate) {
        int workDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek().getValue() <= 5) { // 周一到周五
                workDays++;
            }
            date = date.plusDays(1);
        }
        return workDays;
    }
} 