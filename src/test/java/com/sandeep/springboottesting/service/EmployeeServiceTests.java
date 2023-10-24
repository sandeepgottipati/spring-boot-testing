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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    public void given_when_then() {
        //given - precondition or setup
        Employee employee1=Employee.builder().firstName("jack").lastName("Maguire").id(2L).email("jackmaguire@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        //when -action or behaviour
    List<Employee> employeeList=employeeService.getAllEmployee();

        //then -verify the output
        assertThat(employeeList.size()).isEqualTo(2);
    }
}
