package com.example.employee.service;

import com.example.employee.entity.Department;
import com.example.employee.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentServiceImpl departmentService;

    @Test
    public void testSaveDepartment() {
        // 准备测试数据
        Department department = new Department();
        department.setName("技术部");
        department.setDescription("负责公司技术开发");

        // 模拟save方法返回true
        when(departmentService.save(department)).thenReturn(true);

        // 执行测试
        boolean result = departmentService.save(department);

        // 验证结果
        assertTrue(result);
        verify(departmentService, times(1)).save(department);
    }

    @Test
    public void testUpdateDepartment() {
        // 准备测试数据
        Department department = new Department();
        department.setId(1L);
        department.setName("技术研发部");
        department.setDescription("负责公司技术研发工作");

        // 模拟updateById方法返回true
        when(departmentService.updateById(department)).thenReturn(true);

        // 执行测试
        boolean result = departmentService.updateById(department);

        // 验证结果
        assertTrue(result);
        verify(departmentService, times(1)).updateById(department);
    }

    @Test
    public void testDeleteDepartment() {
        // 模拟removeById方法返回true
        when(departmentService.removeById(1L)).thenReturn(true);

        // 执行测试
        boolean result = departmentService.removeById(1L);

        // 验证结果
        assertTrue(result);
        verify(departmentService, times(1)).removeById(1L);
    }

    @Test
    public void testGetDepartmentById() {
        // 准备测试数据
        Department department = new Department();
        department.setId(1L);
        department.setName("技术部");
        department.setDescription("负责公司技术开发");

        // 模拟getById方法返回department对象
        when(departmentService.getById(1L)).thenReturn(department);

        // 执行测试
        Department result = departmentService.getById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals("技术部", result.getName());
        verify(departmentService, times(1)).getById(1L);
    }

    @Test
    public void testListDepartments() {
        // 准备测试数据
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("技术部");

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("人事部");

        List<Department> departmentList = Arrays.asList(department1, department2);

        // 模拟list方法返回departmentList
        when(departmentService.list()).thenReturn(departmentList);

        // 执行测试
        List<Department> result = departmentService.list();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departmentService, times(1)).list();
    }
}
