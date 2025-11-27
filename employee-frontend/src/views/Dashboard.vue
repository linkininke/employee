<template>
  <div class="dashboard-container">
    <div class="page-header">
      <h1>首页</h1>
    </div>
    
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon employees-icon">👥</div>
        <div class="stat-content">
          <h3>员工总数</h3>
          <p class="stat-number">{{ totalEmployees }}</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon departments-icon">🏢</div>
        <div class="stat-content">
          <h3>部门数量</h3>
          <p class="stat-number">{{ totalDepartments }}</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon male-icon">👨</div>
        <div class="stat-content">
          <h3>男性员工</h3>
          <p class="stat-number">{{ maleEmployees }}</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon female-icon">👩</div>
        <div class="stat-content">
          <h3>女性员工</h3>
          <p class="stat-number">{{ femaleEmployees }}</p>
        </div>
      </div>
    </div>
    
    <div class="recent-employees">
      <h2>最近入职员工</h2>
      <div class="employee-list">
        <div v-for="employee in recentEmployees" :key="employee.id" class="employee-item">
          <div class="employee-info">
            <h4>{{ employee.name }}</h4>
            <p>{{ employee.department }} - {{ employee.position }}</p>
          </div>
          <div class="employee-date">
            {{ employee.hireDate }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useEmployeeStore } from '../store/employee'

const employeeStore = useEmployeeStore()

// 计算统计数据
const totalEmployees = computed(() => employeeStore.employees.length)

const totalDepartments = computed(() => {
  const departments = new Set(employeeStore.employees.map(emp => emp.department))
  return departments.size
})

const maleEmployees = computed(() => {
  return employeeStore.employees.filter(emp => emp.gender === '男').length
})

const femaleEmployees = computed(() => {
  return employeeStore.employees.filter(emp => emp.gender === '女').length
})

const recentEmployees = computed(() => {
  return [...employeeStore.employees]
    .sort((a, b) => new Date(b.hireDate) - new Date(a.hireDate))
    .slice(0, 5)
})

onMounted(() => {
  employeeStore.fetchEmployees()
})
</script>

<style scoped>
.dashboard-container {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.page-header {
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  color: #333;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background-color: white;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s;
}

.stat-card:hover {
  background-color: #f5f7fa;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  font-size: 36px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background-color: #f5f7fa;
}

.employees-icon {
  background-color: #e6f7ff;
}

.departments-icon {
  background-color: #f6ffed;
}

.male-icon {
  background-color: #fff1f0;
}

.female-icon {
  background-color: #f9f0ff;
}

.stat-content h3 {
  margin: 0 0 8px 0;
  color: #606266;
  font-size: 14px;
  font-weight: 500;
}

.stat-number {
  margin: 0;
  color: #333;
  font-size: 28px;
  font-weight: 600;
}

.recent-employees {
  background-color: white;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 20px;
}

.recent-employees h2 {
  margin: 0 0 16px 0;
  color: #333;
  font-size: 18px;
  font-weight: 600;
}

.employee-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.employee-item {
  background-color: white;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.3s;
}

.employee-item:hover {
  background-color: #f5f7fa;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.employee-info h4 {
  margin: 0 0 4px 0;
  color: #333;
  font-size: 16px;
  font-weight: 600;
}

.employee-info p {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.employee-date {
  color: #909399;
  font-size: 14px;
  font-weight: 500;
}
</style>