package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompanyById(Integer id) {
        companyRepository.deleteById(id);
    }

    public Page<Company> getCompaniesByRange(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Company> getCompaniesByConditions(Integer page, Integer pageSize) {
        List<Company> companies = getAllCompanies();
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            Page<Company> companiesByRange = getCompaniesByRange(page, pageSize);
            companies = companiesByRange.getContent();
        }
        return companies;
    }

    public Company updateCompanyById(Integer id, Company updateCompany) {
        Company company = companyRepository.findById(id).orElse(null);
        if (Objects.nonNull(company)) {
            company.setCompanyName(updateCompany.getCompanyName());
            return companyRepository.save(company);
        }
        return null;
    }
}
