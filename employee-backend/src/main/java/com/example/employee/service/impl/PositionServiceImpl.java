package com.example.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.employee.entity.Position;
import com.example.employee.mapper.PositionMapper;
import com.example.employee.service.PositionService;
import org.springframework.stereotype.Service;

/**
 * 职位Service实现类
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

}