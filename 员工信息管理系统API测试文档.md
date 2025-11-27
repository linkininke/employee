# 员工信息管理系统API测试文档

## 1. 测试环境

- 测试URL: http://localhost:8080
- 测试工具: Postman
- 认证方式: JWT Token

## 2. 认证管理

### 2.1 登录接口

- **请求URL**: `/auth/login`
- **请求方法**: POST
- **请求体**:
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
- **预期响应**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "admin"
  }
  ```

### 2.2 获取当前用户信息

- **请求URL**: `/auth/me`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **预期响应**:
  ```json
  {
    "username": "admin",
    "authorities": ["ROLE_USER"]
  }
  ```

## 3. 员工管理

### 3.1 分页查询员工列表

- **请求URL**: `/api/employees`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页条数，默认10
  - `name`: 员工姓名（可选）
  - `departmentId`: 部门ID（可选）
  - `positionId`: 职位ID（可选）
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "records": [
        {
          "id": 1,
          "name": "张三",
          "gender": "男",
          "age": 25,
          "departmentId": 1,
          "positionId": 1,
          "salary": 5000,
          "hireDate": "2023-01-01",
          "createTime": "2023-01-01T00:00:00",
          "updateTime": "2023-01-01T00:00:00",
          "deleted": 0
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "pages": 1
    }
  }
  ```

### 3.2 根据ID查询员工

- **请求URL**: `/api/employees/{id}`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 员工ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "id": 1,
      "name": "张三",
      "gender": "男",
      "age": 25,
      "departmentId": 1,
      "positionId": 1,
      "salary": 5000,
      "hireDate": "2023-01-01",
      "createTime": "2023-01-01T00:00:00",
      "updateTime": "2023-01-01T00:00:00",
      "deleted": 0
    }
  }
  ```

### 3.3 添加员工

- **请求URL**: `/api/employees`
- **请求方法**: POST
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **请求体**:
  ```json
  {
    "name": "李四",
    "gender": "女",
    "age": 30,
    "departmentId": 1,
    "positionId": 2,
    "salary": 6000,
    "hireDate": "2023-02-01"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "添加成功"
  }
  ```

### 3.4 更新员工

- **请求URL**: `/api/employees/{id}`
- **请求方法**: PUT
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **路径参数**:
  - `id`: 员工ID
- **请求体**:
  ```json
  {
    "name": "李四",
    "gender": "女",
    "age": 31,
    "departmentId": 1,
    "positionId": 2,
    "salary": 6500,
    "hireDate": "2023-02-01"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "更新成功"
  }
  ```

### 3.5 删除员工

- **请求URL**: `/api/employees/{id}`
- **请求方法**: DELETE
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 员工ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "删除成功"
  }
  ```

### 3.6 批量删除员工

- **请求URL**: `/api/employees/batch`
- **请求方法**: DELETE
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **请求体**:
  ```json
  [1, 2, 3]
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "批量删除成功"
  }
  ```

### 3.7 导出员工数据为Excel

- **请求URL**: `/api/employees/export`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **预期响应**: Excel文件下载

### 3.8 导入员工数据

- **请求URL**: `/api/employees/import`
- **请求方法**: POST
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: multipart/form-data
  ```
- **请求体**: 表单数据，包含Excel文件
  - `file`: 员工数据Excel文件
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "导入成功，共导入10条数据"
  }
  ```

## 4. 部门管理

### 4.1 获取所有部门

- **请求URL**: `/api/departments`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": [
      {
        "id": 1,
        "name": "技术部",
        "description": "负责公司技术开发",
        "createTime": "2023-01-01T00:00:00",
        "updateTime": "2023-01-01T00:00:00",
        "deleted": 0
      }
    ]
  }
  ```

### 4.2 分页获取部门

- **请求URL**: `/api/departments/page`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `pageNum`: 页码，默认1
  - `pageSize`: 每页条数，默认10
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "records": [
        {
          "id": 1,
          "name": "技术部",
          "description": "负责公司技术开发",
          "createTime": "2023-01-01T00:00:00",
          "updateTime": "2023-01-01T00:00:00",
          "deleted": 0
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "pages": 1
    }
  }
  ```

### 4.3 根据ID获取部门

- **请求URL**: `/api/departments/{id}`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 部门ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "id": 1,
      "name": "技术部",
      "description": "负责公司技术开发",
      "createTime": "2023-01-01T00:00:00",
      "updateTime": "2023-01-01T00:00:00",
      "deleted": 0
    }
  }
  ```

### 4.4 添加部门

- **请求URL**: `/api/departments`
- **请求方法**: POST
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **请求体**:
  ```json
  {
    "name": "人事部",
    "description": "负责公司人事管理"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "添加成功"
  }
  ```

### 4.5 更新部门

- **请求URL**: `/api/departments/{id}`
- **请求方法**: PUT
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **路径参数**:
  - `id`: 部门ID
- **请求体**:
  ```json
  {
    "name": "人力资源部",
    "description": "负责公司人力资源管理"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "更新成功"
  }
  ```

### 4.6 删除部门

- **请求URL**: `/api/departments/{id}`
- **请求方法**: DELETE
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 部门ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "删除成功"
  }
  ```

### 4.7 根据名称搜索部门

- **请求URL**: `/api/departments/search`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `name`: 部门名称
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": [
      {
        "id": 1,
        "name": "技术部",
        "description": "负责公司技术开发",
        "createTime": "2023-01-01T00:00:00",
        "updateTime": "2023-01-01T00:00:00",
        "deleted": 0
      }
    ]
  }
  ```

## 5. 职位管理

### 5.1 获取所有职位

- **请求URL**: `/api/positions`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": [
      {
        "id": 1,
        "name": "前端开发工程师",
        "description": "负责前端开发工作",
        "createTime": "2023-01-01T00:00:00",
        "updateTime": "2023-01-01T00:00:00",
        "deleted": 0
      }
    ]
  }
  ```

### 5.2 分页获取职位

- **请求URL**: `/api/positions/page`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `pageNum`: 页码，默认1
  - `pageSize`: 每页条数，默认10
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "records": [
        {
          "id": 1,
          "name": "前端开发工程师",
          "description": "负责前端开发工作",
          "createTime": "2023-01-01T00:00:00",
          "updateTime": "2023-01-01T00:00:00",
          "deleted": 0
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "pages": 1
    }
  }
  ```

### 5.3 根据ID获取职位

- **请求URL**: `/api/positions/{id}`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 职位ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "id": 1,
      "name": "前端开发工程师",
      "description": "负责前端开发工作",
      "createTime": "2023-01-01T00:00:00",
      "updateTime": "2023-01-01T00:00:00",
      "deleted": 0
    }
  }
  ```

### 5.4 添加职位

- **请求URL**: `/api/positions`
- **请求方法**: POST
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **请求体**:
  ```json
  {
    "name": "后端开发工程师",
    "description": "负责后端开发工作"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "添加成功"
  }
  ```

### 5.5 更新职位

- **请求URL**: `/api/positions/{id}`
- **请求方法**: PUT
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **路径参数**:
  - `id`: 职位ID
- **请求体**:
  ```json
  {
    "name": "高级后端开发工程师",
    "description": "负责高级后端开发工作"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "更新成功"
  }
  ```

### 5.6 删除职位

- **请求URL**: `/api/positions/{id}`
- **请求方法**: DELETE
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **路径参数**:
  - `id`: 职位ID
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "删除成功"
  }
  ```

### 5.7 根据名称搜索职位

- **请求URL**: `/api/positions/search`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `name`: 职位名称
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": [
      {
        "id": 1,
        "name": "前端开发工程师",
        "description": "负责前端开发工作",
        "createTime": "2023-01-01T00:00:00",
        "updateTime": "2023-01-01T00:00:00",
        "deleted": 0
      }
    ]
  }
  ```

## 6. 薪资记录管理

### 6.1 分页查询薪资记录

- **请求URL**: `/api/salary-records`
- **请求方法**: GET
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  ```
- **请求参数**:
  - `page`: 页码，默认1
  - `size`: 每页条数，默认10
  - `employeeId`: 员工ID（可选）
  - `year`: 年份（可选）
  - `month`: 月份（可选）
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "success",
    "data": {
      "records": [
        {
          "id": 1,
          "employeeId": 1,
          "amount": 5000,
          "year": 2023,
          "month": 1,
          "paymentDate": "2023-01-15",
          "createTime": "2023-01-01T00:00:00",
          "updateTime": "2023-01-01T00:00:00",
          "deleted": 0
        }
      ],
      "total": 1,
      "size": 10,
      "current": 1,
      "pages": 1
    }
  }
  ```

### 6.2 添加薪资记录

- **请求URL**: `/api/salary-records`
- **请求方法**: POST
- **请求头**:
  ```
  Authorization: Bearer {{token}}
  Content-Type: application/json
  ```
- **请求体**:
  ```json
  {
    "employeeId": 1,
    "amount": 5000,
    "year": 2023,
    "month": 2,
    "paymentDate": "2023-02-15"
  }
  ```
- **预期响应**:
  ```json
  {
    "code": 200,
    "msg": "添加成功"
  }
  ```

## 7. 测试注意事项

1. 所有需要认证的接口必须在请求头中携带有效的JWT Token
2. 测试前确保数据库中已有基础数据（部门、职位等）
3. 测试文件导入功能时，确保Excel文件格式符合要求
4. 测试删除功能时，注意数据关联关系
5. 测试批量操作时，确保参数格式正确

## 8. 测试结果记录

| 接口名称 | 请求方法 | 请求URL | 测试结果 | 备注 |
|---------|---------|--------|---------|------|
| 登录接口 | POST | /auth/login | ✅ | 成功 |
| 获取当前用户信息 | GET | /auth/me | ✅ | 成功 |
| 分页查询员工列表 | GET | /api/employees | ✅ | 成功 |
| 添加员工 | POST | /api/employees | ✅ | 成功 |
| 更新员工 | PUT | /api/employees/{id} | ✅ | 成功 |
| 删除员工 | DELETE | /api/employees/{id} | ✅ | 成功 |
| 批量删除员工 | DELETE | /api/employees/batch | ✅ | 成功 |
| 导出员工数据 | GET | /api/employees/export | ✅ | 成功 |
| 导入员工数据 | POST | /api/employees/import | ✅ | 成功 |
| 获取所有部门 | GET | /api/departments | ✅ | 成功 |
| 添加部门 | POST | /api/departments | ✅ | 成功 |
| 更新部门 | PUT | /api/departments/{id} | ✅ | 成功 |
| 删除部门 | DELETE | /api/departments/{id} | ✅ | 成功 |
| 获取所有职位 | GET | /api/positions | ✅ | 成功 |
| 添加职位 | POST | /api/positions | ✅ | 成功 |
| 更新职位 | PUT | /api/positions/{id} | ✅ | 成功 |
| 删除职位 | DELETE | /api/positions/{id} | ✅ | 成功 |
| 分页查询薪资记录 | GET | /api/salary-records | ✅ | 成功 |
| 添加薪资记录 | POST | /api/salary-records | ✅ | 成功 |
