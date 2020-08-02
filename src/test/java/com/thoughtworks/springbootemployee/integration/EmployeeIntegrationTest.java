package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void afterAll() {
        this.employeeRepository.deleteAll();
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() throws Exception {

        List<Employee> employees = this.saveEmployee();

        mockMvc.perform(get("/employees/" + employees.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(employees.get(0).getName()))
                .andExpect(jsonPath("$.age").value(employees.get(0).getAge()))
                .andExpect(jsonPath("$.gender").value(employees.get(0).getGender()))
                .andExpect(jsonPath("$.salary").value(employees.get(0).getSalary()))
                .andExpect(jsonPath("$.companyId").value(employees.get(0).getCompanyId()));
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() throws Exception {

        List<Employee> employees = this.saveEmployee();

        mockMvc.perform(get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_return_employees_when_get_employess_given_nothing() throws Exception {
        List<Employee> employees = this.saveEmployee();

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employees.size())));
    }

    @Test
    void should_return_employees_when_get_range_employee_given_page_and_pageSize() throws Exception {
        List<Employee> employees = this.saveEmployee();

        mockMvc.perform(get("/employees?page=1&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber());
    }


    private List<Employee> saveEmployee() {

        Company company = new Company(1, "alibaba", 0, emptyList());
        Company company1 = this.companyRepository.save(company);

        List<Employee> employees = this.getMockEmployee();
        for (Employee employee : employees) {
                employee.setCompanyId(company1.getId());
        }
        return this.employeeRepository.saveAll(employees);
    }

    private List<Employee> getMockEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "zhangsan", 20, "male", 2000, null));
        employees.add(new Employee(2, "lisi", 21, "female", 200000, null));
        employees.add(new Employee(3, "wangwu", 58, "male", 60000, null));
        employees.add(new Employee(4, "zhaoliu", 47, "male", 6001, null));
        employees.add(new Employee(5, "Bruno", 20, "male", 6000, null));
        return employees;
    }

}
