# 员工信息管理系统

## 项目介绍

员工信息管理系统是一个基于前后端分离架构的企业级应用，用于管理企业员工的基本信息、部门结构、职位信息以及薪资记录。系统提供了完整的CRUD操作，支持数据导入导出，以及用户认证授权功能。

## 技术栈

### 后端
- **语言**: Java 17
- **框架**: Spring Boot 3.2.0
- **ORM**: MyBatis Plus 3.5.7
- **数据库**: MySQL 8.x
- **认证**: JWT (JSON Web Token) + Spring Security
- **Excel处理**: EasyExcel 3.3.3
- **代码简化**: Lombok
- **API文档**: SpringDoc OpenAPI (Swagger)
- **构建工具**: Maven

### 前端
- **框架**: Vue 3
- **构建工具**: Vite 7.2.4
- **路由**: Vue Router 4.6.3
- **状态管理**: Pinia 3.0.4
- **UI组件库**: Element Plus
- **HTTP客户端**: Axios 1.13.2
- **测试框架**: Vitest 4.0.14

## 项目结构

```
employee/
├── employee-backend/          # 后端代码
│   ├── src/main/java/com/example/employee/  # 后端主代码
│   │   ├── common/            # 公共类
│   │   ├── config/            # 配置类
│   │   ├── controller/        # 控制器
│   │   ├── entity/            # 实体类
│   │   ├── mapper/            # Mapper接口
│   │   ├── service/           # 服务层
│   │   └── utils/             # 工具类
│   └── src/main/resources/    # 资源文件
│       ├── db/                # 数据库初始化脚本
│       └── application.yml    # 应用配置
├── employee-frontend/         # 前端代码
│   ├── src/                   # 前端主代码
│   │   ├── api/               # API请求
│   │   ├── components/        # 组件
│   │   ├── router/            # 路由配置
│   │   ├── store/             # 状态管理
│   │   ├── utils/             # 工具类
│   │   ├── views/             # 页面组件
│   │   ├── App.vue            # 根组件
│   │   └── main.js            # 入口文件
│   └── package.json           # 前端依赖
├── 员工信息管理系统API测试文档.md  # API测试文档
└── 部署指南.md                # 部署指南
```

## 功能模块

### 1. 用户认证
- 登录/注销
- JWT令牌管理
- 权限控制

### 2. 员工管理
- 员工信息的增删改查
- 员工信息导入导出（Excel）
- 员工详情查看

### 3. 部门管理
- 部门信息的增删改查
- 部门结构管理

### 4. 职位管理
- 职位信息的增删改查
- 职位与部门关联

### 5. 薪资记录管理
- 薪资记录的增删改查
- 薪资统计分析

### 6. 仪表盘
- 数据统计展示
- 关键指标监控

## 快速开始

### 环境要求

- **Java**: 17+
- **Node.js**: 16+
- **MySQL**: 8.x

### 后端部署

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd employee/employee-backend
   ```

2. **配置数据库**
   - 创建数据库：`employee_db`
   - 执行初始化脚本：`src/main/resources/db/init.sql`
   - 修改配置文件：`src/main/resources/application.yml`，配置数据库连接信息

3. **构建项目**
   ```bash
   mvn clean package
   ```

4. **运行项目**
   ```bash
   java -jar target/employee-backend-1.0.0-SNAPSHOT.jar
   ```
   或使用IDE直接运行 `EmployeeApplication.java`

### 前端部署

1. **进入前端目录**
   ```bash
   cd employee/employee-frontend
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **启动开发服务器**
   ```bash
   npm run dev
   ```

4. **构建生产版本**
   ```bash
   npm run build
   ```
   构建产物将输出到 `dist` 目录

## 访问地址

- **前端开发环境**: http://localhost:5173
- **后端API地址**: http://localhost:8080
- **API文档**: http://localhost:8080/swagger-ui.html (如果配置了Swagger)

## 默认账号

- **用户名**: admin
- **密码**: 123456

## 主要API接口

### 认证相关
- `POST /api/auth/login` - 用户登录

### 员工管理
- `GET /api/employee` - 获取员工列表
- `POST /api/employee` - 添加员工
- `PUT /api/employee/{id}` - 更新员工
- `DELETE /api/employee/{id}` - 删除员工
- `GET /api/employee/{id}` - 获取员工详情
- `POST /api/employee/import` - 导入员工数据
- `GET /api/employee/export` - 导出员工数据

### 部门管理
- `GET /api/department` - 获取部门列表
- `POST /api/department` - 添加部门
- `PUT /api/department/{id}` - 更新部门
- `DELETE /api/department/{id}` - 删除部门

### 职位管理
- `GET /api/position` - 获取职位列表
- `POST /api/position` - 添加职位
- `PUT /api/position/{id}` - 更新职位
- `DELETE /api/position/{id}` - 删除职位

### 薪资记录管理
- `GET /api/salary-record` - 获取薪资记录列表
- `POST /api/salary-record` - 添加薪资记录
- `PUT /api/salary-record/{id}` - 更新薪资记录
- `DELETE /api/salary-record/{id}` - 删除薪资记录

## 开发说明

### 后端开发

1. **代码规范**: 遵循阿里巴巴Java开发规范
2. **日志**: 使用SLF4J + Logback
3. **异常处理**: 全局异常处理
4. **参数校验**: 使用JSR-303

### 前端开发

1. **代码规范**: 遵循Vue 3官方风格指南
2. **组件命名**: 采用PascalCase命名规范
3. **API请求**: 统一封装在`src/api`目录下
4. **状态管理**: 按模块划分store

## 测试

### 后端测试

```bash
cd employee-backend
mvn test
```

### 前端测试

```bash
cd employee-frontend
npm run test
```

## 部署

详细部署步骤请参考 [部署指南.md](部署指南.md)

## API测试

API测试文档请参考 [员工信息管理系统API测试文档.md](员工信息管理系统API测试文档.md)

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request

## 联系方式

如有问题，请联系项目负责人
