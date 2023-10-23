package com.sandeep.springboottesting.repository;

import com.sandeep.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder().firstName("sandeep")
                .lastName("gottipati")
                .email("snchowdary7@gmail.com")
                .build();
    }

    @Test
    @DisplayName("JUnit test for employee save Operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given -precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("sandeep")
//                .lastName("gottipati")
//                .email("snchowdary7@gmail.com")
//                .build();


        //when - action or behavior
        Employee savedEmployee = employeeRepository.save(employee);

        //then -verify or output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("find all employees list")
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("saketh").lastName("kosuri").email("kosuri.saketh16@gmail.com").build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        //when -action or behaviour
        List<Employee> employeeList = employeeRepository.findAll();


        //then -verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).hasSize(2);
    }

    @Test

    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        //when -action or behaviour
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then -verify the output

        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo("sandeep");
    }

    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        //when -action or behaviour
        Employee emp = employeeRepository.findByEmail(employee.getEmail()).get();

        //then -verify the output
        assertThat(emp).isNotNull();
        assertThat(emp.getId()).isGreaterThan(0);
        assertThat(emp.getFirstName()).isEqualTo("sandeep");
    }

    @Test
    @DisplayName("Update Employee Test")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup

        employeeRepository.save(employee);
        //when -action or behaviour
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setFirstName("sreya");
        savedEmployee.setLastName("kotha");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then -verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("sreya");
    }

    //junit test for delete employee operation.
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        //given - precondition or setup

        employeeRepository.save(employee);
        //when -action or behaviour
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        //then -verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom query using JPQL with index.
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        //when -action or behaviour
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then -verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getLastName()).isEqualTo(employee.getLastName());

    }

    //JUnit test for custom query using named parameters.
    @Test
    @DisplayName("JUnit test for custom query using JPQL with named Params")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        //when -action or behaviour
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then -verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for custom query using native SQL using index
    @Test
    @DisplayName("JUnit test for native sql query with index ")
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        //when -action or behaviour
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        //then -verify the output
        assertThat(savedEmployee).isNotNull();

    }

    @Test
    @DisplayName("JUnit test for native sql query with named params")
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee);
        //when -action or behaviour
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());
        //then -verify the output
        assertThat(savedEmployee).isNotNull();

    }
}
