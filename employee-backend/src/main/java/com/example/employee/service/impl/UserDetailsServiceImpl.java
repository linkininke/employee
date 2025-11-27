package com.example.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.employee.entity.SysUser;
import com.example.employee.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名加载用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("根据用户名加载用户信息，用户名: {}", username);
        
        // 对于admin用户，直接返回硬编码的用户信息，使用明文密码"admin"
        if ("admin".equals(username)) {
            logger.info("使用硬编码的admin用户，密码为明文admin");
            return User.withUsername("admin")
                    .password("admin")
                    .roles("ADMIN")
                    .build();
        }
        
        // 从数据库中查询用户
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);

        logger.info("查询到的用户信息: {}", sysUser);
        
        // 如果用户不存在，抛出异常
        if (sysUser == null) {
            logger.error("用户不存在，用户名: {}", username);
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        logger.info("用户密码: {}", sysUser.getPassword());
        
        // 将SysUser转换为UserDetails对象
        return new User(sysUser.getUsername(), sysUser.getPassword(), new ArrayList<>());
    }

}