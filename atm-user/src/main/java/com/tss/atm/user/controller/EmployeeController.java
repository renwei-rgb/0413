package com.tss.atm.user.controller;

import com.tss.atm.common.result.Result;
import com.tss.atm.user.entity.Employee;
import com.tss.atm.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    
    private final EmployeeService employeeService;

    // ... existing code ...

    @GetMapping
    public Result<List<Employee>> getAllEmployees() {
        return Result.success(employeeService.list());
    }

    // ... existing code ...
    
    @PostMapping
    public Result<Boolean> createEmployee(@RequestBody Employee employee) {
        return Result.success(employeeService.save(employee));
    }
    
    @PutMapping
    public Result<Boolean> updateEmployee(@RequestBody Employee employee) {
        return Result.success(employeeService.updateById(employee));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteEmployee(@PathVariable Long id) {
        return Result.success(employeeService.removeById(id));
    }
    
    @GetMapping("/{employeeId}")
    public Result<Employee> getEmployee(@PathVariable String employeeId) {
        return Result.success(employeeService.getByEmployeeId(employeeId));
    }
} 