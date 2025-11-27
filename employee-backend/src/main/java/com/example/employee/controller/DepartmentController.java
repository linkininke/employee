package com.example.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.employee.common.Result;
import com.example.employee.entity.Department;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门Controller
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门
     */
    @GetMapping
    public Result<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.list();
        return Result.success(departments);
    }

    /**
     * 分页获取部门
     */
    @GetMapping("/page")
    public Result<Page<Department>> getDepartmentsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Department> page = new Page<>(pageNum, pageSize);
        Page<Department> departmentPage = departmentService.page(page);
        return Result.success(departmentPage);
    }

    /**
     * 根据ID获取部门
     */
    @GetMapping("/{id}")
    public Result<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        if (department == null) {
            return Result.fail("部门不存在");
        }
        return Result.success(department);
    }

    /**
     * 添加部门
     */
    @PostMapping
    public Result<?> addDepartment(@RequestBody Department department) {
        boolean saved = departmentService.save(department);
        if (saved) {
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     * 更新部门
     */
    @PutMapping("/{id}")
    public Result<?> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        department.setId(id);
        boolean updated = departmentService.updateById(department);
        if (updated) {
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteDepartment(@PathVariable Long id) {
        boolean deleted = departmentService.removeById(id);
        if (deleted) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据名称搜索部门
     */
    @GetMapping("/search")
    public Result<List<Department>> searchDepartments(@RequestParam String name) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Department> departments = departmentService.list(queryWrapper);
        return Result.success(departments);
    }

}