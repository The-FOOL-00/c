package com.example.demo;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private final EmployeeRepository repo;
    private final MongoTemplate mongoTemplate;

    public EmployeeController(
            EmployeeRepository repo,
            MongoTemplate mongoTemplate) {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }


// DISPLAY IT EMPLOYEES

@GetMapping("/it")
public List<Employee> getITEmployees() {
    List<Employee> result = new java.util.ArrayList<>();

    for (Employee e : repo.findAll()) {
        if ("IT".equalsIgnoreCase(e.getDeptName())) {
            result.add(e);
        }
    }

    return result;
}

// ADD EMPLOYEE

@PostMapping
public Employee addEmployee(@RequestBody Employee employee) {
    return repo.save(employee);
}
  
    // EMPLOYEES IN IT WITH THEIR PAYROLLS

    @GetMapping("/with-payroll")
    public List<Document> getEmployeesWithPayroll() {

        LookupOperation lookup = lookup(
            "Payroll",
            "_id",
            "employeeId",
            "payrollDetails"
        );

        Aggregation aggregation = newAggregation(
            match(Criteria.where("deptName").is("IT")),
            lookup
        );

        AggregationResults<Document> results =
            mongoTemplate.aggregate(
                aggregation, "Employee", Document.class
            );

        return results.getMappedResults();
    }
}
