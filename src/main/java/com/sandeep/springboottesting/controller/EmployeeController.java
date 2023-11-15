package com.sandeep.springboottesting.controller;

import com.sandeep.springboottesting.model.Employee;
import com.sandeep.springboottesting.service.EmployeeService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        return employeeService.getEmployeeById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id")long id,@RequestBody Employee employee){
       return employeeService.getEmployeeById(id)
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    Employee updatedEmployee=employeeService.updateEmployee(savedEmployee);
                    return new ResponseEntity(updatedEmployee,HttpStatus.OK);
                }).orElseGet(()->ResponseEntity.notFound().build());

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Employee Deleted Successfully",HttpStatus.OK);
    }
}
