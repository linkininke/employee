package com.example.employee.controller;

import com.example.employee.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录请求体
     */
    static class LoginRequest {
        private String username;
        private String password;

        // getter and setter
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 登录响应体
     */
    static class LoginResponse {
        private String token;
        private String username;

        public LoginResponse(String token, String username) {
            this.token = token;
            this.username = username;
        }

        // getter
        public String getToken() {
            return token;
        }

        public String getUsername() {
            return username;
        }
    }

    /**
     * 用户登录接口
     */
    @Operation(summary = "用户登录", description = "用户登录接口，返回JWT令牌")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("收到登录请求，用户名: {}", loginRequest.getUsername());
        
        // 认证用户
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        logger.info("用户认证成功，用户名: {}", loginRequest.getUsername());
        
        // 将认证信息存入SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成JWT令牌
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        logger.info("生成JWT令牌成功，用户名: {}", loginRequest.getUsername());
        
        // 返回响应
        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername()));
    }

    /**
     * 获取当前用户信息接口
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的信息")
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        response.put("authorities", userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }

}