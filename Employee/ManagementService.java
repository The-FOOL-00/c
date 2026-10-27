package com.example.studentmanager.service;

import com.example.studentmanager.model.Employee;
import com.example.studentmanager.model.Payroll;
import com.example.studentmanager.repository.EmployeeRepository;
import com.example.studentmanager.repository.PayrollRepository;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ManagementService {

    private final EmployeeRepository employeeRepo;
    private final PayrollRepository payrollRepo;

    public ManagementService(EmployeeRepository employeeRepo,
                             PayrollRepository payrollRepo) {
        this.employeeRepo = employeeRepo;
        this.payrollRepo = payrollRepo;
    }

    // GET IT EMPLOYEES

    public List<Employee> getITEmployees() {
        List<Employee> result = new ArrayList<>();

        for (Employee e : employeeRepo.findAll()) {
            if ("IT".equalsIgnoreCase(e.getDeptName())) {
                result.add(e);
            }
        }

        return result;
    }

    // POST EMPLOYEE

    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    // GET PAYROLLS OF IT EMPLOYEES

    public List<Payroll> getITPayrolls() {
        Set<String> ids = new HashSet<>();

        for (Employee e : getITEmployees()) {
            ids.add(e.getEmployeeId());
        }

        List<Payroll> result = new ArrayList<>();

        for (Payroll p : payrollRepo.findAll()) {
            if (ids.contains(p.getEmployeeId())) {
                result.add(p);
            }
        }

        return result;
    }
}
