package com.sandeep.springboottesting.service.impl;

import com.sandeep.springboottesting.exception.ResourceNotFoundException;
import com.sandeep.springboottesting.model.Employee;
import com.sandeep.springboottesting.repository.EmployeeRepository;
import com.sandeep.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

private EmployeeRepository employeeRepository;

    //because constructor injection does not require Autowired annotation.
public EmployeeServiceImpl( EmployeeRepository employeeRepository){
    this.employeeRepository=employeeRepository;
}
    @Override
    public Employee saveEmployee(Employee employee) {
    Optional<Employee> savedEmployee=employeeRepository.findByEmail(employee.getEmail());
    if(savedEmployee.isPresent()){
        throw new ResourceNotFoundException("employee already exits with given email :"+employee.getEmail());
    }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
     return   employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }


}
