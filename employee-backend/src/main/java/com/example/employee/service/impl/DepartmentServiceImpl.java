package com.example.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * 部门Service实现类
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}