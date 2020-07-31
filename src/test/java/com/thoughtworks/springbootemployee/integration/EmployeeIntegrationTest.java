package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MockMvc mockMvc;


    @AfterEach
    public void afterAll() {
        this.employeeRepository.deleteAll();
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() throws Exception {
        List<Employee> employees = this.saveEmployee();

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(employees.get(0).getName()))
                .andExpect(jsonPath("$.age").value(employees.get(0).getAge()))
                .andExpect(jsonPath("$.gender").value(employees.get(0).getGender()))
                .andExpect(jsonPath("$.salary").value(employees.get(0).getSalary()))
                .andExpect(jsonPath("$.companyId").value(employees.get(0).getCompanyId()));
    }

    @Test
    void should_return_employees_when_get_employees_given_nothing() {
        List<Employee> employees = this.saveEmployee();
    }





    private List<Employee> saveEmployee() {

        Company company = new Company(1, "alibaba", 0, emptyList());
        this.companyRepository.save(company);

        List<Employee> employees = this.getMockEmployee();
        return this.employeeRepository.saveAll(employees);
    }

    private List<Employee> getMockEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "zhangsan", 20, "male", 2000, 1));
        employees.add(new Employee(2, "lisi", 21, "female", 200000, 1));
        employees.add(new Employee(3, "wangwu", 58, "male", 60000, 1));
        employees.add(new Employee(4, "zhaoliu", 47, "male", 6001, 1));
        employees.add(new Employee(5, "Bruno", 20, "male", 6000, 1));
        return employees;
    }

}
