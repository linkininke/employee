package com.example.employee.entity.dto;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 员工Excel导出DTO
 */
@Data
public class EmployeeExcelDTO {

    @ExcelProperty(value = "ID", index = 0)
    @ColumnWidth(10)
    private Long id;

    @ExcelProperty(value = "姓名", index = 1)
    @ColumnWidth(15)
    private String name;

    @ExcelProperty(value = "性别", index = 2)
    @ColumnWidth(10)
    private String gender;

    @ExcelProperty(value = "年龄", index = 3)
    @ColumnWidth(10)
    private Integer age;

    @ExcelProperty(value = "部门", index = 4)
    @ColumnWidth(20)
    private String departmentName;

    @ExcelProperty(value = "职位", index = 5)
    @ColumnWidth(20)
    private String positionName;

    @ExcelProperty(value = "薪资", index = 6)
    @ColumnWidth(15)
    private BigDecimal salary;

    @ExcelProperty(value = "入职日期", index = 7)
    @ColumnWidth(15)
    private LocalDate hireDate;

}