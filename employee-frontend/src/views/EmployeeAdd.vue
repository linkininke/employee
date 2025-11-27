<template>
  <div class="employee-add-container">
    <div class="page-header">
      <h1>添加员工</h1>
      <router-link to="/employees" class="back-btn">← 返回员工列表</router-link>
    </div>
    
    <EmployeeForm @submit="handleAddEmployee" @cancel="handleCancel" />
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import EmployeeForm from '../components/EmployeeForm.vue'
import { useEmployeeStore } from '../store/employee'

const router = useRouter()
const employeeStore = useEmployeeStore()

const handleAddEmployee = async (formData) => {
  try {
    await employeeStore.addEmployee(formData)
    alert('员工添加成功')
    router.push('/employees')
  } catch (error) {
    console.error('添加员工失败:', error)
    alert('添加员工失败，请重试')
  }
}

const handleCancel = () => {
  router.push('/employees')
}
</script>

<style scoped>
.employee-add-container {
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
</style>