package com.example.studentmanager.repository;

import com.example.studentmanager.model.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PayrollRepository
        extends MongoRepository<Payroll, String> {
}
