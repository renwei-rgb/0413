package com.tss.atm.controller;

import com.tss.atm.common.result.Result;
import com.tss.atm.entity.Employee;
import com.tss.atm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
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