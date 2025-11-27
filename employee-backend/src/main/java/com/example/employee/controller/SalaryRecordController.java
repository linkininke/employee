package com.example.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.employee.common.Result;
import com.example.employee.entity.SalaryRecord;
import com.example.employee.service.SalaryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资记录Controller
 */
@RestController
@RequestMapping("/api/salary-records")
public class SalaryRecordController {

    @Autowired
    private SalaryRecordService salaryRecordService;

    /**
     * 获取所有薪资记录
     */
    @GetMapping
    public Result<List<SalaryRecord>> getAllSalaryRecords() {
        List<SalaryRecord> salaryRecords = salaryRecordService.list();
        return Result.success(salaryRecords);
    }

    /**
     * 分页获取薪资记录
     */
    @GetMapping("/page")
    public Result<Page<SalaryRecord>> getSalaryRecordsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalaryRecord> page = new Page<>(pageNum, pageSize);
        Page<SalaryRecord> salaryRecordPage = salaryRecordService.page(page);
        return Result.success(salaryRecordPage);
    }

    /**
     * 根据ID获取薪资记录
     */
    @GetMapping("/{id}")
    public Result<SalaryRecord> getSalaryRecordById(@PathVariable Long id) {
        SalaryRecord salaryRecord = salaryRecordService.getById(id);
        if (salaryRecord == null) {
            return Result.fail("薪资记录不存在");
        }
        return Result.success(salaryRecord);
    }

    /**
     * 根据员工ID获取薪资记录
     */
    @GetMapping("/employee/{employeeId}")
    public Result<List<SalaryRecord>> getSalaryRecordsByEmployeeId(@PathVariable Long employeeId) {
        QueryWrapper<SalaryRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", employeeId);
        List<SalaryRecord> salaryRecords = salaryRecordService.list(queryWrapper);
        return Result.success(salaryRecords);
    }

    /**
     * 添加薪资记录
     */
    @PostMapping
    public Result<?> addSalaryRecord(@RequestBody SalaryRecord salaryRecord) {
        // 计算总薪资
        salaryRecord.setTotalSalary(
            salaryRecord.getBasicSalary().add(salaryRecord.getBonus())
                .add(salaryRecord.getAllowance())
                .subtract(salaryRecord.getDeduction())
        );
        boolean saved = salaryRecordService.save(salaryRecord);
        if (saved) {
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     * 更新薪资记录
     */
    @PutMapping("/{id}")
    public Result<?> updateSalaryRecord(@PathVariable Long id, @RequestBody SalaryRecord salaryRecord) {
        // 计算总薪资
        salaryRecord.setTotalSalary(
            salaryRecord.getBasicSalary().add(salaryRecord.getBonus())
                .add(salaryRecord.getAllowance())
                .subtract(salaryRecord.getDeduction())
        );
        salaryRecord.setId(id);
        boolean updated = salaryRecordService.updateById(salaryRecord);
        if (updated) {
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }

    /**
     * 删除薪资记录
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteSalaryRecord(@PathVariable Long id) {
        boolean deleted = salaryRecordService.removeById(id);
        if (deleted) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

}