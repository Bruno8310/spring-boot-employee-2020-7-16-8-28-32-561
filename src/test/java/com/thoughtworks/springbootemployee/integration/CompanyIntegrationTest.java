package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MockMvc mockMvc;

    @AfterAll
    public void afterAll() {
        this.companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_hit_get_companies_given_nothing() throws Exception {
        Company company = getMockCompany().get(0);
        this.companyRepository.save(company);
        List<Company> companies = getMockCompany();
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value(companies.get(0).getCompanyName()))
                .andExpect(jsonPath("$[0].employeesNumber").value(companies.get(0).getEmployeesNumber()));

    }

    private List<Company> getMockCompany() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "OOCL", 1, emptyList()));
        return companies;
    }
}
