package com.example.employee.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.employee.common.Result;
import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Position;
import com.example.employee.entity.dto.EmployeeExcelDTO;
import com.example.employee.service.DepartmentService;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工控制器
 */
@Tag(name = "员工管理", description = "员工相关接口")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    /**
     * 分页查询员工列表
     */
    @Operation(summary = "分页查询员工列表", description = "分页查询员工列表，支持按姓名、部门ID、职位ID筛选")
    @GetMapping
    public Result<Page<Employee>> list(@RequestParam(defaultValue = "1") Integer page, 
                                               @RequestParam(defaultValue = "10") Integer size, 
                                               @RequestParam(required = false) String name, 
                                               @RequestParam(required = false) Long departmentId, 
                                               @RequestParam(required = false) Long positionId) {
        // 创建分页对象
        Page<Employee> pageInfo = new Page<>(page, size);

        // 构建查询条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Employee::getName, name);
        }
        if (departmentId != null) {
            queryWrapper.eq(Employee::getDepartmentId, departmentId);
        }
        if (positionId != null) {
            queryWrapper.eq(Employee::getPositionId, positionId);
        }

        // 执行分页查询
        employeeService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    /**
     * 根据ID查询员工
     */
    @Operation(summary = "根据ID查询员工", description = "根据ID查询员工详细信息")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return Result.fail("员工不存在");
        }
        return Result.success(employee);
    }

    /**
     * 添加员工
     */
    @Operation(summary = "添加员工", description = "添加新员工")
    @PostMapping
    public Result<?> save(@RequestBody Employee employee) {
        employeeService.save(employee);
        return Result.success("添加成功");
    }

    /**
     * 更新员工
     */
    @Operation(summary = "更新员工", description = "根据ID更新员工信息")
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.updateById(employee);
        return Result.success("更新成功");
    }

    /**
     * 删除员工
     */
    @Operation(summary = "删除员工", description = "根据ID删除员工")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        employeeService.removeById(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除员工
     */
    @Operation(summary = "批量删除员工", description = "根据ID列表批量删除员工")
    @DeleteMapping("/batch")
    public Result<?> batchDelete(@RequestBody List<Long> ids) {
        employeeService.removeByIds(ids);
        return Result.success("批量删除成功");
    }

    /**
     * 导出员工数据为Excel
     */
    @Operation(summary = "导出员工数据为Excel", description = "导出所有员工数据为Excel文件")
    @GetMapping("/export")
    public void exportEmployeesToExcel(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("员工数据", "UTF-8") + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        // 获取所有员工数据
        List<Employee> employees = employeeService.list();

        // 获取所有部门和职位数据，用于转换名称
        List<Department> departments = departmentService.list();
        List<Position> positions = positionService.list();

        // 转换为ExcelDTO列表
        List<EmployeeExcelDTO> excelDTOs = employees.stream().map(employee -> {
            EmployeeExcelDTO dto = new EmployeeExcelDTO();
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setGender(employee.getGender());
            dto.setAge(employee.getAge());
            
            // 获取部门名称
            String departmentName = departments.stream()
                    .filter(dept -> dept.getId().equals(employee.getDepartmentId()))
                    .map(Department::getName)
                    .findFirst().orElse("");
            dto.setDepartmentName(departmentName);
            
            // 获取职位名称
            String positionName = positions.stream()
                    .filter(pos -> pos.getId().equals(employee.getPositionId()))
                    .map(Position::getName)
                    .findFirst().orElse("");
            dto.setPositionName(positionName);
            
            dto.setSalary(employee.getSalary());
            dto.setHireDate(employee.getHireDate());
            return dto;
        }).collect(Collectors.toList());

        // 导出Excel
        EasyExcel.write(response.getOutputStream(), EmployeeExcelDTO.class)
                .sheet("员工数据")
                .doWrite(excelDTOs);
    }

    /**
     * 导入员工数据
     */
    @Operation(summary = "导入员工数据", description = "从Excel文件导入员工数据")
    @PostMapping("/import")
    public Result<?> importEmployees(@RequestParam("file") MultipartFile file) throws IOException {
        // 获取所有部门和职位数据，用于转换ID
        List<Department> departments = departmentService.list();
        List<Position> positions = positionService.list();

        // 解析Excel文件
        List<EmployeeExcelDTO> excelDTOs = EasyExcel.read(file.getInputStream(), EmployeeExcelDTO.class, null)
                .sheet()
                .doReadSync();

        // 转换为Employee实体列表
        List<Employee> employees = excelDTOs.stream().map(dto -> {
            Employee employee = new Employee();
            employee.setName(dto.getName());
            employee.setGender(dto.getGender());
            employee.setAge(dto.getAge());
            
            // 获取部门ID
            Long departmentId = departments.stream()
                    .filter(dept -> dept.getName().equals(dto.getDepartmentName()))
                    .map(Department::getId)
                    .findFirst().orElse(null);
            employee.setDepartmentId(departmentId);
            
            // 获取职位ID
            Long positionId = positions.stream()
                    .filter(pos -> pos.getName().equals(dto.getPositionName()))
                    .map(Position::getId)
                    .findFirst().orElse(null);
            employee.setPositionId(positionId);
            
            employee.setSalary(dto.getSalary());
            employee.setHireDate(dto.getHireDate());
            return employee;
        }).collect(Collectors.toList());

        // 批量导入
        boolean saved = employeeService.saveBatch(employees);
        if (saved) {
            return Result.success("导入成功，共导入" + employees.size() + "条数据");
        }
        return Result.fail("导入失败");
    }

}