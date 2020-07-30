package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmployeeServiceTest {
    @Test
    void should_return_updated_employee_when_update_given_employee_id_and_employee_info() throws NotFoundException, IllegalOperationException {
        // given
        Employee employee = getMockEmploy();
        Employee employee1 = getMockEmploy();
        employee1.setName("lisi");
        employee1.setAge(20);
        employee1.setGender("female");
        employee1.setSalary(5000);

        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);

        given(mockedEmployeeRepository.findById(2)).willReturn(Optional.of(employee));

        given(mockedEmployeeRepository.save(employee1)).willReturn(employee1);

        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee employeeResult = employeeService.updateEmployeeById(2, employee1);
        // then
        assertEquals(employee1.getName(), employeeResult.getName());
        assertEquals(employee1.getAge(), employeeResult.getAge());
        assertEquals(employee1.getGender(), employeeResult.getGender());
        assertEquals(employee1.getSalary(), employee1.getSalary());
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_id() throws NotFoundException {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findById(1)).willReturn(Optional.of(getMockEmploy()));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        Employee employee = employeeService.getEmployeeById(1);
        // then
        assertEquals(2, employee.getId());
    }

    @Test
    void should_return_1_when_add_employee_given_employee() {
        // given
        Employee employee = getMockEmploy();
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.save(employee)).willReturn(getMockEmploy());
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
        verify(mockedEmployeeRepository).deleteById(2);
    }

    @Test
    void should_return_employees_when_get_employees_by_gender_given_gender() {
        // given
        EmployeeRepository mockedEmployeeRepository = mock(EmployeeRepository.class);
        given(mockedEmployeeRepository.findByGender("male")).willReturn(Arrays.asList(getMockEmploy(),
                getMockEmploy()));
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
                Arrays.asList(getMockEmploy(),
                        getMockEmploy()));
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
                        getMockEmploy(),
                        getMockEmploy())));
        EmployeeService employeeService = new EmployeeService(mockedEmployeeRepository);
        // when
        List<Employee> employees = employeeService.getEmployeesByPage(1, 5).toList();
        // then
        assertEquals(2, employees.size());
    }

    private Employee getMockEmploy() {
        return new Employee(2, "bruno", 20, "male", 2000, 1);
    }

}


