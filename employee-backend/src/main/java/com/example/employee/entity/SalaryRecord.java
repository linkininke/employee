package com.example.employee.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 薪资记录实体类
 */
@Data
@TableName("salary_record")
public class SalaryRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 基本工资
     */
    private BigDecimal basicSalary;

    /**
     * 奖金
     */
    private BigDecimal bonus;

    /**
     * 津贴
     */
    private BigDecimal allowance;

    /**
     * 扣款
     */
    private BigDecimal deduction;

    /**
     * 总薪资
     */
    private BigDecimal totalSalary;

    /**
     * 生效日期
     */
    private LocalDate effectiveDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识：0-未删除，1-已删除
     */
    @TableLogic
    private Integer deleted;

}