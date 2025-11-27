<template>
  <div class="employee-detail-container">
    <div class="page-header">
      <h1>员工详情</h1>
      <div class="header-actions">
        <router-link v-if="employeeStore.currentEmployee" :to="`/employees/edit/${employeeStore.currentEmployee.id}`" class="edit-btn">编辑</router-link>
        <router-link to="/employees" class="back-btn">← 返回员工列表</router-link>
      </div>
    </div>
    
    <div v-if="loading" class="loading-state">
      <p>加载中...</p>
    </div>
    
    <div v-else-if="employeeStore.currentEmployee" class="detail-card">
      <div class="detail-header">
        <h2>{{ employeeStore.currentEmployee.name }}</h2>
        <div class="basic-info">
          <span class="gender-tag">{{ employeeStore.currentEmployee.gender }}</span>
          <span class="age">{{ employeeStore.currentEmployee.age }}岁</span>
          <span class="department">{{ getDepartmentName(employeeStore.currentEmployee.departmentId) }}</span>
        </div>
      </div>
      
      <div class="detail-content">
        <div class="detail-section">
          <h3>基本信息</h3>
          <div class="info-grid">
            <div class="info-item">
              <label>员工ID:</label>
              <span>{{ employeeStore.currentEmployee.id }}</span>
            </div>
            <div class="info-item">
              <label>姓名:</label>
              <span>{{ employeeStore.currentEmployee.name }}</span>
            </div>
            <div class="info-item">
              <label>性别:</label>
              <span>{{ employeeStore.currentEmployee.gender }}</span>
            </div>
            <div class="info-item">
              <label>年龄:</label>
              <span>{{ employeeStore.currentEmployee.age }}岁</span>
            </div>
            <div class="info-item">
              <label>部门:</label>
              <span>{{ getDepartmentName(employeeStore.currentEmployee.departmentId) }}</span>
            </div>
            <div class="info-item">
              <label>职位:</label>
              <span>{{ getPositionName(employeeStore.currentEmployee.positionId) }}</span>
            </div>
            <div class="info-item">
              <label>薪资:</label>
              <span>{{ employeeStore.currentEmployee.salary }}</span>
            </div>
            <div class="info-item">
              <label>入职日期:</label>
              <span>{{ employeeStore.currentEmployee.hireDate }}</span>
            </div>
          </div>
        </div>
        
        <div class="detail-section">
          <h3>联系信息</h3>
          <div class="info-grid">
            <div class="info-item">
              <label>电话:</label>
              <span>{{ employeeStore.currentEmployee.phone || '未填写' }}</span>
            </div>
            <div class="info-item">
              <label>邮箱:</label>
              <span>{{ employeeStore.currentEmployee.email || '未填写' }}</span>
            </div>
            <div class="info-item full-width">
              <label>地址:</label>
              <span>{{ employeeStore.currentEmployee.address || '未填写' }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div v-else class="empty-state">
      <p>未找到员工信息</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useEmployeeStore } from '../store/employee'
import { getDepartmentList } from '../api/department'
import { getPositionList } from '../api/position'

const router = useRouter()
const route = useRoute()
const employeeStore = useEmployeeStore()
const loading = ref(true)
const departments = ref([])
const positions = ref([])

// 获取部门列表
const fetchDepartments = async () => {
  try {
    const response = await getDepartmentList()
    departments.value = response.data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  }
}

// 获取职位列表
const fetchPositions = async () => {
  try {
    const response = await getPositionList()
    positions.value = response.data
  } catch (error) {
    console.error('获取职位列表失败:', error)
  }
}

// 根据部门ID获取部门名称
const getDepartmentName = (departmentId) => {
  const department = departments.value.find(dept => dept.id === departmentId)
  return department ? department.name : ''
}

// 根据职位ID获取职位名称
const getPositionName = (positionId) => {
  const position = positions.value.find(pos => pos.id === positionId)
  return position ? position.name : ''
}

const fetchEmployee = async () => {
  try {
    const id = route.params.id
    // 并行获取部门、职位和员工详情
    await Promise.all([fetchDepartments(), fetchPositions(), employeeStore.fetchEmployeeById(id)])
  } catch (error) {
    console.error('获取员工详情失败:', error)
    alert('获取员工详情失败，请重试')
    router.push('/employees')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchEmployee()
})
</script>

<style scoped>
.employee-detail-container {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  color: #333;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.edit-btn,
.back-btn {
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
  display: inline-block;
}

.edit-btn {
  background-color: #e6a23c;
  color: white;
}

.edit-btn:hover {
  background-color: #ebb563;
}

.back-btn {
  background-color: #909399;
  color: white;
}

.back-btn:hover {
  background-color: #a6a9ad;
}

.detail-card {
  background-color: #f5f7fa;
  border-radius: 8px;
  padding: 24px;
}

.detail-header {
  text-align: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.detail-header h2 {
  margin: 0 0 12px 0;
  color: #333;
  font-size: 24px;
}

.basic-info {
  display: flex;
  justify-content: center;
  gap: 16px;
  align-items: center;
}

.gender-tag {
  padding: 4px 12px;
  background-color: #409eff;
  color: white;
  border-radius: 12px;
  font-size: 12px;
}

.age, .department {
  color: #606266;
  font-size: 14px;
}

.detail-section {
  margin-bottom: 24px;
}

.detail-section h3 {
  margin: 0 0 16px 0;
  color: #333;
  font-size: 16px;
  font-weight: 600;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 16px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  background-color: white;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.info-item.full-width {
  grid-column: 1 / -1;
}

.info-item label {
  color: #909399;
  font-size: 14px;
  font-weight: 500;
}

.info-item span {
  color: #333;
  font-size: 14px;
  font-weight: 500;
}

.loading-state {
  text-align: center;
  padding: 60px 0;
  color: #409eff;
  font-weight: 500;
}

.empty-state {
  text-align: center;
  padding: 60px 0;
  color: #909399;
  font-weight: 500;
}
</style>