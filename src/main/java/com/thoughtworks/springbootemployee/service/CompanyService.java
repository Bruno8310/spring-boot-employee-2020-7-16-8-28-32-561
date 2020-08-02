package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) throws NotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (Objects.isNull(company)) {
            throw new NotFoundException();
        }
        return company.get();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompanyById(Integer id) {
        companyRepository.deleteById(id);
    }

    public Page<Company> getCompaniesByRange(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    public List<Company> getCompaniesByConditions(Integer page, Integer pageSize) throws NotFoundException {
        List<Company> companies;

        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            Page<Company> companiesByRange = getCompaniesByRange(page, pageSize);
            companies = companiesByRange.getContent();
        } else {
            companies = getAllCompanies();
        }
        return companies;
}

    public Company updateCompanyById(Integer id, Company updateCompany) throws NotFoundException, IllegalOperationException {
        if (id.equals(updateCompany.getId())) {
            throw new IllegalOperationException();
        }
        Optional<Company> company = companyRepository.findById(id);
        if (Objects.nonNull(company)) {
            if (Objects.nonNull(updateCompany.getEmployeesNumber())) {
                company.get().setCompanyName(updateCompany.getCompanyName());
            }
            if (Objects.nonNull(updateCompany.getEmployeesNumber())) {
                company.get().setEmployeesNumber(updateCompany.getEmployeesNumber());
            }
            if (Objects.nonNull(updateCompany.getEmployees())) {
                company.get().setEmployees(updateCompany.getEmployees());
            }
        } else {
            throw new NotFoundException();
        }
            return company.get();
    }
}
