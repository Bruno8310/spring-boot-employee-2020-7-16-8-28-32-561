package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
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

    public Employee updateEmployeeById(Integer id, Employee updatedEmployee) throws NotFoundException, IllegalOperationException {
        if (id.equals(updatedEmployee.getId())) {
            throw new IllegalOperationException();
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if (Objects.nonNull(employee)) {
            if (Objects.nonNull(updatedEmployee.getName())) {
                employee.get().setName(updatedEmployee.getName());
            }
            if (Objects.nonNull(updatedEmployee.getAge())) {
                employee.get().setAge(updatedEmployee.getAge());
            }
            if (Objects.nonNull(updatedEmployee.getGender())) {
                employee.get().setGender(updatedEmployee.getGender());
            }
            if (Objects.nonNull(updatedEmployee.getSalary())) {
                employee.get().setSalary(updatedEmployee.getSalary());
            }
        } else {
            throw new NotFoundException();
        }
        employeeRepository.save(updatedEmployee);
        return employee.get();
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
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            return employeeRepository.findAll(PageRequest.of(page, pageSize));
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByConditions(String gender, Integer page, Integer pageSize) throws NotFoundException {

        List<Employee> employees = getAllEmployees();
        if (Objects.nonNull(gender) && !gender.isEmpty()) {
            employees = getEmployeesByGender(gender);
        }
        Page<Employee> employeesByPage = getEmployeesByPage(page, pageSize);

        if (Objects.nonNull(page) && Objects.nonNull(pageSize) && Objects.nonNull(employeesByPage)) {
            employees = employeesByPage.getContent();
        }
        return employees;
    }
}
