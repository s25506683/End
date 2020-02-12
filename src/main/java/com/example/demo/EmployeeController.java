package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
  private List<Employee> employeeList = new ArrayList<>();
  private int count=0;
  public EmployeeController(){
    employeeList.add(new Employee(1, "Arpit", "IT"));
    employeeList.add(new Employee(2, "Sanjeev", "IT"));
    employeeList.add(new Employee(3, "Ben", "IT"));
    count = 3;
  }

 @GetMapping("/employee")
    public List<Employee> get() {
        return employeeList;
    }   
}

