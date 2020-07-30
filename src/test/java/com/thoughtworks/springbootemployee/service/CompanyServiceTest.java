package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {
    @Test
    void should_return_companies_when_get_companies_given_no_parameter() {
        // given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findAll()).willReturn(Arrays.asList(getMockCompany()));
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        List<Company> companies = companyService.getAllCompanies();
        // then
        assertEquals(1, companies.size());
    }


    @Test
    void should_return_company_when_get_company_by_id_given_id() throws NotFoundException {
        // given
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.findById(1)).willReturn(Optional.of(getMockCompany()));
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company company = companyService.getCompanyById(1);
        // then
        assertEquals("alibaba", company.getCompanyName());
    }

    @Test
    void should_return_company_when_add_company_given_company() {
        // given
        Company company = getMockCompany();
        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
        given(mockedCompanyRepository.save(company)).willReturn(getMockCompany());
        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company newCompany = companyService.addCompany(company);
        // then
        assertEquals(company.getCompanyName(), newCompany.getCompanyName());
    }

    @Test
    void should_return_void_when_update_company_given_company_id_and_company() throws NotFoundException {
        // given
        Company company = getMockCompany();
        Company company1 = getMockCompany();
        company1.setCompanyName("test");
        company1.setEmployeesNumber(100);

        CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);

        given(mockedCompanyRepository.findById(anyInt())).willReturn(Optional.of(getMockCompany()));

        given(mockedCompanyRepository.save(any())).willReturn(company);

        CompanyService companyService = new CompanyService(mockedCompanyRepository);
        // when
        Company updatedCompany = companyService.updateCompanyById(1, company1);
        // then
        assertEquals(company.getCompanyName(), updatedCompany.getCompanyName());
    }

    @Test
    void should_return_companies_when_get_companies_by_range_given_page_and_pageSize() {
        // given
        CompanyRepository mockCompanyRepository = mock(CompanyRepository.class);
        given(mockCompanyRepository.findAll(PageRequest.of(1, 2))).
                willReturn(new PageImpl<>(Arrays.asList(getMockCompany())));
        CompanyService companyService = new CompanyService(mockCompanyRepository);
        // when
        List<Company> companies = companyService.getCompaniesByRange(1, 2).toList();
        // then
        assertEquals(1, companies.size());
    }

    private Company getMockCompany() {
        return new Company(1, "alibaba", 2,
                Arrays.asList(new Employee(1, "zhangsan", 20, "male", 200, 1),
                        new Employee(2, "lisi", 50, "male", 5000, 1)));
    }

}
