package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void afterAll() {
        this.companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_hit_get_companies_given_nothing() throws Exception {
        List<Company> companies = this.saveCompany();
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(companies.size())));

    }

    @Test
    void should_return_company_when_add_a_company_given_company() throws Exception {
        Company company = new Company(1, "huawei", 1, emptyList());
        String companyContent = "{\n" +
                "    \"companyName\": \"huawei\",\n" +
                "    \"employeesNumber\": 1\n" +
                "}";
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));
    }

    @Test
    void should_return_new_company_when_update_old_company_given_comany_and_id() throws Exception {
        List<Company> companyList = this.saveCompany();

        Company company = new Company(1, "tencent", 2, emptyList());

        String companyContent = "{\n" +
                "    \"companyName\": \"tencent\",\n" +
                "    \"employeesNumber\": 2\n" +
                "}";

        mockMvc.perform(put("/companies/"+companyList.get(0).getId()).contentType(MediaType.APPLICATION_JSON).content(companyContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));
    }

    @Test
    void should_return_company_when_get_company_by_id_given_id() throws Exception {
        List<Company> companies = this.saveCompany();
        mockMvc.perform(get("/companies/"+companies.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value(companies.get(0).getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(companies.get(0).getEmployeesNumber()));
    }


    @Test
    void should_return_companies_when_get_companies_by_range_given_page_and_pageSize() throws Exception {
        List<Company> companies = this.saveCompany();

        mockMvc.perform(get("/companies?page=1&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value(companies.get(0).getCompanyName()))
                .andExpect(jsonPath("$[0].employeesNumber").value(companies.get(0).getEmployeesNumber()));
    }

    @Test
    void should_return_void_when_delete_company_given_company_id() throws Exception {
        List<Company> companies = this.saveCompany();
        mockMvc.perform(delete("/companies/"+companies.get(0).getId()))
                .andExpect(status().isOk());
    }

    private List<Company> saveCompany() {
        List<Company> companies = this.getMockCompany();
        return this.companyRepository.saveAll(companies);
    }

    private List<Company> getMockCompany() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "OOCL", 1, emptyList()));
        companies.add(new Company(2, "aiqiyi", 1, emptyList()));
        companies.add(new Company(3, "tencent", 1, emptyList()));
        companies.add(new Company(4, "huawei", 1, emptyList()));
        companies.add(new Company(5, "baidu", 1, emptyList()));
        return companies;
    }

}
