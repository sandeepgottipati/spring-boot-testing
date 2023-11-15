package com.sandeep.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandeep.springboottesting.model.Employee;
import com.sandeep.springboottesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTests {
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder().firstName("sandeep").lastName("gottipati").email("sn@gmail.com").build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));
        //when -action or behaviour

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));
        //then -verify the output
        result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName()))).andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName()))).andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //JUnit test for get all Employees.
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        //given - precondition or setup
        List<Employee> employeeList = List.of(Employee.builder().firstName("sandeep").lastName("G").email("sn76453@gmail.com").build(), Employee.builder().firstName("saketh").lastName("kosuri").email("the@gmail.com").build());
        BDDMockito.given(employeeService.getAllEmployee()).willReturn(employeeList);
        //when -action or behaviour
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));
        //then -verify the output
        result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));

    }

    //positive scenario.
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given - precondition or setup
        Long empId = 1L;
        Employee employee = Employee.builder().firstName("sandee").lastName("gottipati").email("sn@gmail.com").build();
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.of(employee));

        //when -action or behaviour
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", empId));

        //then -verify the output
        result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));
    }

    //Negative scenario.
    @Test
    @DisplayName("GetEmployeeById_Negative_Scenario")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnNotFound() throws Exception {
        //given - precondition or setup
        Long empId = 1L;
        Employee employee = Employee.builder().firstName("sandee").lastName("gottipati").email("sn@gmail.com").build();
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.empty());

        //when -action or behaviour
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", empId));

        //then -verify the output
        result.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //update employee given id and value.
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        //given - precondition or setup
        Long empId = 1L;
        Employee savedEmployee = Employee.builder().firstName("sandeep").lastName("gotti").email("sn@gmail.com").build();
        Employee updatedEmployee = Employee.builder().firstName("saketh").lastName("kosuri").email("sn@gmail.com").build();
        BDDMockito.given(employeeService.getEmployeeById(empId)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        //when -action or behaviour
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", empId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));
        //then -verify the output
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())));
    }
     @Test
         public void givenEmployeeId_whenDeleteEmployee_thenReturnOK() throws Exception {
             //given - precondition or setup
                Long id=1L;
                BDDMockito.willDoNothing().given(employeeService).deleteEmployeeById(id);

             //when -action or behaviour
               ResultActions result= mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",id));
             //then -verify the output
         result.andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(MockMvcResultHandlers.print());
         }
}
