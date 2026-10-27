package com.example.studentmanager.repository;

import com.example.studentmanager.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository
        extends MongoRepository<Employee, String> {
}
