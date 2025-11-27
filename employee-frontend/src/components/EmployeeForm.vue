<template>
  <div class="employee-form-container">
    <form @submit.prevent="handleSubmit">
      <div class="form-row">
        <div class="form-item">
          <label for="name">姓名 <span class="required">*</span></label>
          <input type="text" id="name" v-model="form.name" placeholder="请输入姓名" required>
        </div>
        <div class="form-item">
          <label for="gender">性别 <span class="required">*</span></label>
          <select id="gender" v-model="form.gender" required>
            <option value="">请选择性别</option>
            <option value="男">男</option>
            <option value="女">女</option>
          </select>
        </div>
      </div>
      
      <div class="form-row">
        <div class="form-item">
          <label for="age">年龄 <span class="required">*</span></label>
          <input type="number" id="age" v-model="form.age" placeholder="请输入年龄" min="18" max="65" required>
        </div>
        <div class="form-item">
          <label for="departmentId">部门 <span class="required">*</span></label>
          <select id="departmentId" v-model="form.departmentId" required>
            <option value="">请选择部门</option>
            <option v-for="dept in departments" :key="dept.id" :value="dept.id">{{ dept.name }}</option>
          </select>
        </div>
      </div>
      
      <div class="form-row">
        <div class="form-item">
          <label for="positionId">职位 <span class="required">*</span></label>
          <select id="positionId" v-model="form.positionId" required>
            <option value="">请选择职位</option>
            <option v-for="pos in positions" :key="pos.id" :value="pos.id">{{ pos.name }}</option>
          </select>
        </div>
        <div class="form-item">
          <label for="salary">薪资 <span class="required">*</span></label>
          <input type="number" id="salary" v-model="form.salary" placeholder="请输入薪资" step="0.01" min="0" required>
        </div>
      </div>
      
      <div class="form-row">
        <div class="form-item">
          <label for="hireDate">入职日期 <span class="required">*</span></label>
          <input type="date" id="hireDate" v-model="form.hireDate" required>
        </div>
      </div>
      
      <div class="form-actions">
        <button type="button" class="cancel-btn" @click="$emit('cancel')">取消</button>
        <button type="submit" class="submit-btn">{{ isEdit ? '保存修改' : '添加员工' }}</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { getDepartmentList } from '../api/department'
import { getPositionList } from '../api/position'

const props = defineProps({
  employee: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['submit', 'cancel'])

const isEdit = ref(!!props.employee)
const departments = ref([])
const positions = ref([])

const form = ref({
  name: '',
  gender: '',
  age: '',
  departmentId: '',
  positionId: '',
  salary: '',
  hireDate: ''
})

const resetForm = () => {
  form.value = {
    name: '',
    gender: '',
    age: '',
    departmentId: '',
    positionId: '',
    salary: '',
    hireDate: ''
  }
}

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

// 监听employee变化，更新表单数据
watch(() => props.employee, (newEmployee) => {
  if (newEmployee) {
    isEdit.value = true
    form.value = {
      name: newEmployee.name || '',
      gender: newEmployee.gender || '',
      age: newEmployee.age || '',
      departmentId: newEmployee.departmentId || '',
      positionId: newEmployee.positionId || '',
      salary: newEmployee.salary || '',
      hireDate: newEmployee.hireDate || ''
    }
  } else {
    isEdit.value = false
    resetForm()
  }
}, { immediate: true, deep: true })

const handleSubmit = () => {
  emit('submit', form.value)
}

// 组件挂载时获取部门和职位列表
onMounted(() => {
  fetchDepartments()
  fetchPositions()
})
</script>

<style scoped>
.employee-form-container {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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
  color: #333;
  font-weight: 500;
}

.required {
  color: #f56c6c;
}

.form-item input,
.form-item select,
.form-item textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s;
  font-family: inherit;
}

.form-item input:focus,
.form-item select:focus,
.form-item textarea:focus {
  outline: none;
  border-color: #409eff;
}

.form-item textarea {
  resize: vertical;
  min-height: 80px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 30px;
}

.cancel-btn,
.submit-btn {
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
}

.cancel-btn {
  background-color: #909399;
  color: white;
}

.cancel-btn:hover {
  background-color: #a6a9ad;
}

.submit-btn {
  background-color: #67c23a;
  color: white;
}

.submit-btn:hover {
  background-color: #85ce61;
}
</style>