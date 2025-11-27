<template>
  <div class="employee-edit-container">
    <div class="page-header">
      <h1>编辑员工</h1>
      <router-link to="/employees" class="back-btn">← 返回员工列表</router-link>
    </div>
    
    <div v-if="employeeStore.loading" class="loading-state">
      <p>加载中...</p>
    </div>
    
    <EmployeeForm v-else-if="employeeStore.currentEmployee" :employee="employeeStore.currentEmployee" @submit="handleUpdateEmployee" @cancel="handleCancel" />
    
    <div v-else class="empty-state">
      <p>未找到员工信息</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import EmployeeForm from '../components/EmployeeForm.vue'
import { useEmployeeStore } from '../store/employee'

const router = useRouter()
const route = useRoute()
const employeeStore = useEmployeeStore()

const fetchEmployee = async () => {
  try {
    const id = route.params.id
    await employeeStore.fetchEmployeeById(id)
  } catch (error) {
    console.error('获取员工信息失败:', error)
    alert('获取员工信息失败，请重试')
    router.push('/employees')
  }
}

const handleUpdateEmployee = async (formData) => {
  try {
    const id = route.params.id
    await employeeStore.updateEmployee(id, formData)
    alert('员工信息更新成功')
    router.push('/employees')
  } catch (error) {
    console.error('更新员工信息失败:', error)
    alert('更新员工信息失败，请重试')
  }
}

const handleCancel = () => {
  router.push('/employees')
}

onMounted(() => {
  fetchEmployee()
})
</script>

<style scoped>
.employee-edit-container {
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

.back-btn {
  padding: 8px 16px;
  background-color: #909399;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 14px;
  transition: background-color 0.3s;
}

.back-btn:hover {
  background-color: #a6a9ad;
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