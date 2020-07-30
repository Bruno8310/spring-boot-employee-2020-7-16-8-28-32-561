package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee updateEmployeeById(Integer id, Employee updatedEmployee) throws NotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(Objects.isNull(employee)) {
            throw new NotFoundException();
        }
        Employee employeeUpdated = employee.get();
        employeeUpdated.setName(updatedEmployee.getName());
        employeeUpdated.setAge(updatedEmployee.getAge());
        employeeUpdated.setGender(updatedEmployee.getGender());
        employeeUpdated.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(employeeUpdated);
    }

    public Employee getEmployeeById(Integer id) throws NotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (Objects.isNull(employee)) {
            throw new NotFoundException();
        }
        return employee.get();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Page<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByConditions(String gender, Integer page, Integer pageSize) throws NotFoundException {
        List<Employee> employees = getAllEmployees();
        if (Objects.nonNull(gender) && !gender.isEmpty()) {
            employees = getEmployeesByGender(gender);
        } else {
            throw new NotFoundException();
        }
        Page<Employee> employeesByPage = getEmployeesByPage(page, pageSize);
        if (Objects.nonNull(page) && Objects.nonNull(pageSize) && Objects.nonNull(employeesByPage)) {
            employees = employeesByPage.getContent();
        } else {
            throw new NotFoundException();
        }
        return employees;
    }
}
