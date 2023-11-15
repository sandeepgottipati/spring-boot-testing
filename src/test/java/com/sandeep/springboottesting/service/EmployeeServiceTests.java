package com.sandeep.springboottesting.service;

import com.sandeep.springboottesting.exception.ResourceNotFoundException;
import com.sandeep.springboottesting.model.Employee;
import com.sandeep.springboottesting.repository.EmployeeRepository;
import com.sandeep.springboottesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        // employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().firstName("sandeep").lastName("gottipati").email("dummy@gmail.com").id(1L).build();
    }

    @Test
    @DisplayName("Junit Test for save employee")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup


        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when -action or behaviour
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then -verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for save employee method that returns exception")
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when -action or behaviour
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        //then -verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("JUnit test for get all Employees")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeeList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder().firstName("jack").lastName("Maguire").id(2L).email("jackmaguire@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when -action or behaviour
        List<Employee> employeeList = employeeService.getAllEmployee();

        //then -verify the output
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit test for get all employees method with zero employees")
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when -action or behaviour
        List<Employee> employeeList = employeeService.getAllEmployee();
        //then -verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList).hasSize(0);
    }

    @Test
    @DisplayName("JUnit test for getEmployeeById  Method")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        //when -action or behaviour
        Optional<Employee> savedEmployee = employeeService.getEmployeeById(employee.getId());
        //then -verify the output
        assertThat(savedEmployee.get().getId()).isEqualTo(employee.getId());
    }

    @Test
    @DisplayName("JUnit for updateEmployee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setLastName("kotha");
        employee.setFirstName("shreyas");
        //when -action or behaviour
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then -verify the output
        assertThat(updatedEmployee.getFirstName()).isEqualTo("shreyas");
        assertThat(updatedEmployee.getLastName()).isEqualTo("kotha");
    }
     @Test
     @DisplayName("Junit test for deleteEmployee Method")
         public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
             //given - precondition or setup
                willDoNothing().given(employeeRepository).deleteById(employee.getId());
             //when -action or behaviour
            employeeService.deleteEmployeeById(1l);
             //then -verify the output
         verify(employeeRepository,times(1)).deleteById(employee.getId());
         }

}
