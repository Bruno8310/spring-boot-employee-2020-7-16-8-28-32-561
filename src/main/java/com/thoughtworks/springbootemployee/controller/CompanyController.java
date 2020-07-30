package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompaniesByConditions(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) throws NotFoundException {
        return companyService.getCompaniesByConditions(page, pageSize);
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyByNumber(@PathVariable int companyId) throws NotFoundException {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompanyByNumber(@PathVariable Integer companyId, @RequestBody Company updateCompany) throws NotFoundException {
        return companyService.updateCompanyById(companyId, updateCompany);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompanyById(@PathVariable Integer companyId) {
        companyService.deleteCompanyById(companyId);
    }
}
