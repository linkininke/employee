package com.example.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.employee.entity.SalaryRecord;
import com.example.employee.mapper.SalaryRecordMapper;
import com.example.employee.service.SalaryRecordService;
import org.springframework.stereotype.Service;

/**
 * 薪资记录Service实现类
 */
@Service
public class SalaryRecordServiceImpl extends ServiceImpl<SalaryRecordMapper, SalaryRecord> implements SalaryRecordService {

}