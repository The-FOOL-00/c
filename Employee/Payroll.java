package com.example.studentmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Payroll")
public class Payroll {

    @Id
    private String payrollId;

    private String employeeId;
    private double basicSalary;
    private double allowances;
    private double deductions;
    private double netSalary;
}
