<template>
  <div class="salary-record-edit-container">
    <div class="page-header">
      <h1>编辑薪资记录</h1>
      <router-link to="/salary-records" class="back-btn">返回列表</router-link>
    </div>
    
    <div class="form-container" v-if="!loading">
      <form @submit.prevent="handleSubmit">
        <div class="form-row">
          <div class="form-item">
            <label for="employeeId">员工 <span class="required">*</span></label>
            <select id="employeeId" v-model="form.employeeId" required>
              <option value="">请选择员工</option>
              <option v-for="employee in employees" :key="employee.id" :value="employee.id">{{ employee.name }}</option>
            </select>
          </div>
          <div class="form-item">
            <label for="basicSalary">基本工资 <span class="required">*</span></label>
            <input 
              type="number" 
              id="basicSalary" 
              v-model.number="form.basicSalary" 
              placeholder="请输入基本工资" 
              step="0.01" 
              min="0" 
              required
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-item">
            <label for="bonus">奖金</label>
            <input 
              type="number" 
              id="bonus" 
              v-model.number="form.bonus" 
              placeholder="请输入奖金" 
              step="0.01" 
              min="0"
            >
          </div>
          <div class="form-item">
            <label for="allowance">津贴</label>
            <input 
              type="number" 
              id="allowance" 
              v-model.number="form.allowance" 
              placeholder="请输入津贴" 
              step="0.01" 
              min="0"
            >
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-item">
            <label for="deduction">扣款</label>
            <input 
              type="number" 
              id="deduction" 
              v-model.number="form.deduction" 
              placeholder="请输入扣款" 
              step="0.01" 
              min="0"
            >
          </div>
          <div class="form-item">
            <label for="effectiveDate">生效日期 <span class="required">*</span></label>
            <input type="date" id="effectiveDate" v-model="form.effectiveDate" required>
          </div>
        </div>
        
        <div class="form-row">
          <div class="form-item full-width">
            <label for="description">描述</label>
            <textarea 
              id="description" 
              v-model="form.description" 
              placeholder="请输入描述" 
              rows="4"
            ></textarea>
          </div>
        </div>
        
        <div class="form-actions">
          <button type="submit" class="submit-btn" :disabled="submitting">
            {{ submitting ? '提交中...' : '提交' }}
          </button>
          <button type="reset" class="reset-btn">重置</button>
        </div>
      </form>
    </div>
    
    <div v-else class="loading-state">
      <p>加载中...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getSalaryRecordById, updateSalaryRecord } from '../api/salaryRecord'
import { getEmployeeList } from '../api/employee'

const router = useRouter()
const route = useRoute()
const salaryRecordId = route.params.id

const employees = ref([])
const loading = ref(true)
const submitting = ref(false)
const form = ref({
  employeeId: '',
  basicSalary: 0,
  bonus: 0,
  allowance: 0,
  deduction: 0,
  effectiveDate: '',
  description: ''
})

// 获取员工列表
const fetchEmployees = async () => {
  try {
    const response = await getEmployeeList({ page: 1, pageSize: 100 }) // 获取所有员工
    employees.value = response.data.records || []
  } catch (error) {
    console.error('获取员工列表失败:', error)
  }
}

// 获取薪资记录详情
const fetchSalaryRecord = async () => {
  try {
    const response = await getSalaryRecordById(salaryRecordId)
    form.value = response.data
  } catch (error) {
    console.error('获取薪资记录详情失败:', error)
    alert('获取薪资记录详情失败，请重试')
    router.push('/salary-records')
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  submitting.value = true
  try {
    await updateSalaryRecord(salaryRecordId, form.value)
    alert('薪资记录更新成功')
    router.push('/salary-records')
  } catch (error) {
    console.error('更新薪资记录失败:', error)
    alert('更新薪资记录失败，请重试')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  // 并行获取员工列表和薪资记录详情
  await Promise.all([fetchEmployees(), fetchSalaryRecord()])
})
</script>

<style scoped>
.salary-record-edit-container {
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
  padding: 10px 16px;
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

.form-container {
  max-width: 800px;
  margin: 0 auto;
}

.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.form-item {
  flex: 1;
  min-width: 250px;
}

.form-item.full-width {
  flex: 100%;
}

.form-item label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
}

.form-item input,
.form-item select,
.form-item textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-item input:focus,
.form-item select:focus,
.form-item textarea:focus {
  outline: none;
  border-color: #409eff;
}

.required {
  color: #f56c6c;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 30px;
  justify-content: flex-end;
}

.submit-btn {
  padding: 10px 20px;
  background-color: #67c23a;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover:not(:disabled) {
  background-color: #85ce61;
}

.submit-btn:disabled {
  background-color: #c0c4cc;
  cursor: not-allowed;
}

.reset-btn {
  padding: 10px 20px;
  background-color: #909399;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.reset-btn:hover {
  background-color: #a6a9ad;
}

.loading-state {
  text-align: center;
  padding: 40px 0;
  color: #409eff;
  font-weight: 500;
}
</style>