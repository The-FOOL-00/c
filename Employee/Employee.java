package com.example.studentmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Employee")
public class Employee {

    @Id
    private String employeeId;

    private String deptId;
    private String deptName;
    private String employeeName;
}
