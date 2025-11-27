<template>
  <div class="department-add-container">
    <div class="page-header">
      <h1>添加部门</h1>
      <router-link to="/departments" class="back-btn">返回列表</router-link>
    </div>
    
    <div class="form-container">
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="name">部门名称 *</label>
          <input 
            type="text" 
            id="name" 
            v-model="form.name" 
            placeholder="请输入部门名称" 
            required
          >
        </div>
        
        <div class="form-group">
          <label for="description">部门描述</label>
          <textarea 
            id="description" 
            v-model="form.description" 
            placeholder="请输入部门描述" 
            rows="4"
          ></textarea>
        </div>
        
        <div class="form-actions">
          <button type="submit" class="submit-btn" :disabled="submitting">
            {{ submitting ? '提交中...' : '提交' }}
          </button>
          <button type="reset" class="reset-btn">重置</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { addDepartment } from '../api/department'

const router = useRouter()
const form = ref({
  name: '',
  description: ''
})
const submitting = ref(false)

const handleSubmit = async () => {
  submitting.value = true
  try {
    await addDepartment(form.value)
    alert('部门添加成功')
    router.push('/departments')
  } catch (error) {
    console.error('添加部门失败:', error)
    alert('添加部门失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.department-add-container {
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
  max-width: 600px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #409eff;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 30px;
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
</style>