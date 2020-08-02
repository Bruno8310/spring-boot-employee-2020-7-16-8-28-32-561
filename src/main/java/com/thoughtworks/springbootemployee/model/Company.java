package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String companyName;
    private int employeesNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private List<Employee> employees;

    public Company() {
    }

    public Company(int id, String companyName, int employeesNumber, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return id == company.id &&
                employeesNumber == company.employeesNumber &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(employees, company.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, employeesNumber, employees);
    }
}
