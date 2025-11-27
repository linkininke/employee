package com.example.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.employee.common.Result;
import com.example.employee.entity.Position;
import com.example.employee.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位Controller
 */
@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 获取所有职位
     */
    @GetMapping
    public Result<List<Position>> getAllPositions() {
        List<Position> positions = positionService.list();
        return Result.success(positions);
    }

    /**
     * 分页获取职位
     */
    @GetMapping("/page")
    public Result<Page<Position>> getPositionsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Position> page = new Page<>(pageNum, pageSize);
        Page<Position> positionPage = positionService.page(page);
        return Result.success(positionPage);
    }

    /**
     * 根据ID获取职位
     */
    @GetMapping("/{id}")
    public Result<Position> getPositionById(@PathVariable Long id) {
        Position position = positionService.getById(id);
        if (position == null) {
            return Result.fail("职位不存在");
        }
        return Result.success(position);
    }

    /**
     * 添加职位
     */
    @PostMapping
    public Result<?> addPosition(@RequestBody Position position) {
        boolean saved = positionService.save(position);
        if (saved) {
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     * 更新职位
     */
    @PutMapping("/{id}")
    public Result<?> updatePosition(@PathVariable Long id, @RequestBody Position position) {
        position.setId(id);
        boolean updated = positionService.updateById(position);
        if (updated) {
            return Result.success("更新成功");
        }
        return Result.fail("更新失败");
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    public Result<?> deletePosition(@PathVariable Long id) {
        boolean deleted = positionService.removeById(id);
        if (deleted) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据名称搜索职位
     */
    @GetMapping("/search")
    public Result<List<Position>> searchPositions(@RequestParam String name) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Position> positions = positionService.list(queryWrapper);
        return Result.success(positions);
    }

}