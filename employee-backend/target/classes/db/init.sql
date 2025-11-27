-- 创建数据库
CREATE DATABASE IF NOT EXISTS employee_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE employee_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建部门表
CREATE TABLE IF NOT EXISTS department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建职位表
CREATE TABLE IF NOT EXISTS `position` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建员工表
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    department_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_name (name),
    INDEX idx_department_id (department_id),
    INDEX idx_position_id (position_id),
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (position_id) REFERENCES `position`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建薪资记录表
CREATE TABLE IF NOT EXISTS salary_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    basic_salary DECIMAL(10, 2) NOT NULL,
    bonus DECIMAL(10, 2) DEFAULT 0.00,
    allowance DECIMAL(10, 2) DEFAULT 0.00,
    deduction DECIMAL(10, 2) DEFAULT 0.00,
    total_salary DECIMAL(10, 2) NOT NULL,
    effective_date DATE NOT NULL,
    description VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_employee_id (employee_id),
    INDEX idx_effective_date (effective_date),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入初始用户数据
INSERT INTO sys_user (username, password) VALUES ('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2');

-- 插入初始部门数据
INSERT INTO department (name, description) VALUES 
('技术部', '负责公司技术开发和维护'),
('人事部', '负责人力资源管理'),
('财务部', '负责公司财务管理'),
('市场部', '负责公司市场推广和销售');

-- 插入初始职位数据
INSERT INTO `position` (name, description) VALUES 
('Java开发工程师', '负责Java应用开发'),
('人事专员', '负责人事招聘和员工管理'),
('技术经理', '负责技术团队管理和项目规划'),
('会计', '负责公司财务核算'),
('市场总监', '负责市场战略规划和执行');

-- 插入初始员工数据
INSERT INTO employee (name, gender, age, department_id, position_id, salary, hire_date) VALUES 
('张三', '男', 28, 1, 1, 12000.00, '2020-03-15'),
('李四', '女', 25, 2, 2, 8000.00, '2021-07-01'),
('王五', '男', 32, 1, 3, 18000.00, '2018-11-20'),
('赵六', '女', 26, 3, 4, 9000.00, '2022-01-10'),
('孙七', '男', 30, 4, 5, 15000.00, '2019-05-25');

-- 插入初始薪资记录
INSERT INTO salary_record (employee_id, basic_salary, bonus, allowance, deduction, total_salary, effective_date, description) VALUES 
(1, 10000.00, 1500.00, 500.00, 0.00, 12000.00, '2020-03-15', '入职薪资'),
(2, 7000.00, 800.00, 200.00, 0.00, 8000.00, '2021-07-01', '入职薪资'),
(3, 15000.00, 2500.00, 500.00, 0.00, 18000.00, '2018-11-20', '入职薪资'),
(4, 8000.00, 800.00, 200.00, 0.00, 9000.00, '2022-01-10', '入职薪资'),
(5, 13000.00, 1500.00, 500.00, 0.00, 15000.00, '2019-05-25', '入职薪资');