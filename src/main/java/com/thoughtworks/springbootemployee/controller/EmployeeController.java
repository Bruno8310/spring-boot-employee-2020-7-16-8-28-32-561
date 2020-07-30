package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployeesByConditions(@RequestParam(value = "gender", required = false) String gender,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) throws NotFoundException {
        return employeeService.getEmployeesByConditions(gender, page, pageSize);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) throws NotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody Employee updatedEmployee) throws NotFoundException {
        return employeeService.updateEmployeeById(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }
}
