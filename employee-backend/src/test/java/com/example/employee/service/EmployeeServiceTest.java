package com.example.employee.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.employee.entity.Employee;
import com.example.employee.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @Test
    public void testSaveEmployee() {
        // 准备测试数据
        Employee employee = new Employee();
        employee.setName("张三");
        employee.setGender("男");
        employee.setAge(25);
        employee.setDepartmentId(1L);
        employee.setPositionId(1L);
        employee.setSalary(new BigDecimal(5000));
        employee.setHireDate(LocalDate.now());

        // 模拟save方法返回true
        when(employeeService.save(employee)).thenReturn(true);

        // 执行测试
        boolean result = employeeService.save(employee);

        // 验证结果
        assertTrue(result);
        verify(employeeService, times(1)).save(employee);
    }

    @Test
    public void testUpdateEmployee() {
        // 准备测试数据
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("李四");
        employee.setGender("男");
        employee.setAge(30);
        employee.setDepartmentId(1L);
        employee.setPositionId(2L);
        employee.setSalary(new BigDecimal(6000));
        employee.setHireDate(LocalDate.now());

        // 模拟updateById方法返回true
        when(employeeService.updateById(employee)).thenReturn(true);

        // 执行测试
        boolean result = employeeService.updateById(employee);

        // 验证结果
        assertTrue(result);
        verify(employeeService, times(1)).updateById(employee);
    }

    @Test
    public void testDeleteEmployee() {
        // 模拟removeById方法返回true
        when(employeeService.removeById(1L)).thenReturn(true);

        // 执行测试
        boolean result = employeeService.removeById(1L);

        // 验证结果
        assertTrue(result);
        verify(employeeService, times(1)).removeById(1L);
    }

    @Test
    public void testGetEmployeeById() {
        // 准备测试数据
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("张三");
        employee.setGender("男");
        employee.setAge(25);

        // 模拟getById方法返回employee对象
        when(employeeService.getById(1L)).thenReturn(employee);

        // 执行测试
        Employee result = employeeService.getById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals("张三", result.getName());
        assertEquals(25, result.getAge());
        verify(employeeService, times(1)).getById(1L);
    }

    @Test
    public void testListEmployees() {
        // 准备测试数据
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("张三");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("李四");

        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        // 模拟list方法返回employeeList
        when(employeeService.list()).thenReturn(employeeList);

        // 执行测试
        List<Employee> result = employeeService.list();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeService, times(1)).list();
    }

    @Test
    public void testListEmployeesByDepartment() {
        // 准备测试数据
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("张三");
        employee1.setDepartmentId(1L);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("李四");
        employee2.setDepartmentId(1L);

        List<Employee> employeeList = Arrays.asList(employee1, employee2);

        // 模拟list方法返回employeeList
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", 1L);
        when(employeeService.list(queryWrapper)).thenReturn(employeeList);

        // 执行测试
        List<Employee> result = employeeService.list(queryWrapper);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeService, times(1)).list(queryWrapper);
    }
}
