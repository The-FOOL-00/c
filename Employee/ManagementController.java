package com.example.studentmanager.controller;

import com.example.studentmanager.model.Employee;
import com.example.studentmanager.model.Payroll;
import com.example.studentmanager.service.ManagementService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ManagementController {

    private final ManagementService service;

    public ManagementController(ManagementService service) {
        this.service = service;
    }

    // GET IT EMPLOYEES

    @GetMapping("/api/employees/it")
    public List<Employee> getITEmployees() {
        return service.getITEmployees();
    }

    // POST EMPLOYEE

    @PostMapping("/api/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return service.addEmployee(employee);
    }

    // GET PAYROLLS OF IT EMPLOYEES

    @GetMapping("/api/payrolls/it")
    public List<Payroll> getITPayrolls() {
        return service.getITPayrolls();
    }
}
