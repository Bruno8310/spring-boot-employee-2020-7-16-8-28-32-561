package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
import static org.mockito.Mockito.verify;

public class EmployeeServiceTest {
    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() {
        // given
        Employee employee = new Employee(1, "lisi", 15, "female", 12200);

        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        // TODO
        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(new Employee(1, "zhangsan", 12, "male", 1200)));
        // TODO
        given(mockedEmployeeRepository.save(employee)).willReturn(employee);

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee employeeResult = employeeService.updateEmployeeById(1, employee);
        // then
        assertEquals(1, employeeResult.getId());
        assertEquals("lisi", employeeResult.getName());
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(new Employee(1, "lisi", 15, "female", 12200)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee employee = employeeService.getEmployeeById(1);
        // then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_1_when_add_employee_given_employee() {
        // given
        Employee employee = new Employee(11, "tom", 49, "male", 1000);
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.save(employee)).willReturn(new Employee(11, "tom", 49, "male", 1000));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee result = employeeService.addEmployee(employee);
        // then
        assertEquals(employee.getId(), result.getId());
    }

    @Test
    void should_judge_when_delete_employee_by_id_given_employee_id() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        employeeService.deleteEmployeeById(2);
        // then
        // TODO
        verify(mockedEmployeeRepository).deleteById(any());
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findByGender("male")).willReturn(Arrays.asList(new Employee(2, "bruno", 20, "male", 2000),
                new Employee(3, "xiaosun", 21, "male", 5000)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        List<Employee> employees = employeeService.getEmployeesByGender("male");
        // then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employees_when_get_all_employee_given_no_parameter() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findAll()).willReturn(
                Arrays.asList(new Employee(2, "bruno", 20, "male", 2000),
                        new Employee(3, "xiaosun", 21, "male", 5000)));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        List<Employee> employees = employeeService.getAllEmployees();
        // then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_employees_when_get_employees_by_range_given_page_and_pageSize() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findAll(PageRequest.of(1, 5))).
                willReturn(new PageImpl<>(Arrays.asList(
                        new Employee(2, "bruno", 20, "male", 2000),
                        new Employee(3, "xiaosun", 21, "male", 5000))));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        List<Employee> employees = employeeService.getEmployeesByPage(1, 5).toList();
        // then
        assertEquals(2, employees.size());
    }

}


