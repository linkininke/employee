<template>
  <div class="salary-record-list-container">
    <div class="page-header">
      <h1>薪资记录管理</h1>
      <div class="header-actions">
        <button 
          class="batch-delete-btn" 
          @click="handleBatchDelete" 
          :disabled="selectedIds.length === 0"
        >
          批量删除
        </button>
        <router-link to="/salary-records/add" class="add-btn">+ 添加薪资记录</router-link>
      </div>
    </div>
    
    <div class="search-box">
      <input type="text" v-model="searchQuery" placeholder="搜索员工姓名" @input="handleSearch">
      <button class="search-btn">搜索</button>
    </div>
    
    <div class="table-container">
      <table class="salary-record-table">
        <thead>
          <tr>
            <th>
              <input type="checkbox" v-model="selectAll" @change="handleSelectAll">
            </th>
            <th>员工</th>
            <th>基本工资</th>
            <th>奖金</th>
            <th>津贴</th>
            <th>扣款</th>
            <th>总薪资</th>
            <th>生效日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="9" class="loading-state">加载中...</td>
          </tr>
          <tr v-else v-for="record in salaryRecords" :key="record.id">
            <td>
              <input 
                type="checkbox" 
                v-model="selectedIds" 
                :value="record.id"
              >
            </td>
            <td>{{ record.employeeName || '-' }}</td>
            <td>{{ record.basicSalary }}</td>
            <td>{{ record.bonus }}</td>
            <td>{{ record.allowance }}</td>
            <td>{{ record.deduction }}</td>
            <td>{{ record.totalSalary }}</td>
            <td>{{ record.effectiveDate }}</td>
            <td class="action-buttons">
              <router-link :to="`/salary-records/edit/${record.id}`" class="edit-btn">编辑</router-link>
              <button class="delete-btn" @click="handleDelete(record.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="!loading && salaryRecords.length === 0" class="empty-state">
      <p>暂无薪资记录数据</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { getSalaryRecordList, deleteSalaryRecord } from '../api/salaryRecord'
import { getEmployeeList } from '../api/employee'

const salaryRecords = ref([])
const employees = ref([])
const loading = ref(false)
const searchQuery = ref('')
const selectedIds = ref([])

// 获取薪资记录列表
const fetchSalaryRecords = async () => {
  loading.value = true
  try {
    const response = await getSalaryRecordList()
    salaryRecords.value = response.data
  } catch (error) {
    console.error('获取薪资记录列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取员工列表
const fetchEmployees = async () => {
  try {
    const response = await getEmployeeList({ page: 1, pageSize: 100 }) // 获取所有员工
    employees.value = response.data.records || []
  } catch (error) {
    console.error('获取员工列表失败:', error)
  }
}

// 根据员工ID获取员工姓名
const getEmployeeName = (employeeId) => {
  const employee = employees.value.find(emp => emp.id === employeeId)
  return employee ? employee.name : ''
}

// 全选功能
const selectAll = computed({
  get() {
    return selectedIds.value.length === salaryRecords.value.length && salaryRecords.value.length > 0
  },
  set(value) {
    if (value) {
      selectedIds.value = salaryRecords.value.map(record => record.id)
    } else {
      selectedIds.value = []
    }
  }
})

const handleSelectAll = () => {
  // 全选功能已通过 computed 属性实现
}

const handleSearch = () => {
  // 搜索功能可以在这里实现
  // 目前只是重新加载列表，后续可以优化为前端过滤或后端搜索
  fetchSalaryRecords()
  // 搜索后清空选择
  selectedIds.value = []
}

const handleDelete = async (id) => {
  if (confirm('确定要删除该薪资记录吗？')) {
    try {
      await deleteSalaryRecord(id)
      // 删除后重新加载列表
      await fetchSalaryRecords()
      // 删除后从选择列表中移除
      selectedIds.value = selectedIds.value.filter(selectedId => selectedId !== id)
    } catch (error) {
      console.error('删除薪资记录失败:', error)
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    alert('请先选择要删除的薪资记录')
    return
  }
  
  if (confirm(`确定要删除选中的 ${selectedIds.value.length} 条薪资记录吗？`)) {
    try {
      // 批量删除，逐个调用删除接口
      for (const id of selectedIds.value) {
        await deleteSalaryRecord(id)
      }
      // 重新加载列表
      await fetchSalaryRecords()
      // 清空选择列表
      selectedIds.value = []
    } catch (error) {
      console.error('批量删除薪资记录失败:', error)
    }
  }
}

onMounted(async () => {
  // 并行获取员工列表和薪资记录列表
  await Promise.all([fetchEmployees(), fetchSalaryRecords()])
})
</script>

<style scoped>
.salary-record-list-container {
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
  gap: 10px;
}

.batch-delete-btn {
  padding: 10px 16px;
  background-color: #f56c6c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.batch-delete-btn:hover:not(:disabled) {
  background-color: #f78989;
}

.batch-delete-btn:disabled {
  background-color: #c0c4cc;
  cursor: not-allowed;
}

.add-btn {
  padding: 10px 16px;
  background-color: #409eff;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 14px;
  transition: background-color 0.3s;
}

.add-btn:hover {
  background-color: #66b1ff;
}

.search-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-box input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.search-box input:focus {
  outline: none;
  border-color: #409eff;
}

.search-btn {
  padding: 10px 16px;
  background-color: #67c23a;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-btn:hover {
  background-color: #85ce61;
}

.table-container {
  overflow-x: auto;
}

.salary-record-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.salary-record-table th,
.salary-record-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.salary-record-table th {
  background-color: #f5f7fa;
  font-weight: 600;
  color: #333;
}

.salary-record-table tr:hover {
  background-color: #f5f7fa;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.edit-btn,
.delete-btn {
  padding: 6px 12px;
  border-radius: 4px;
  text-decoration: none;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s;
  border: none;
}

.edit-btn {
  background-color: #e6a23c;
  color: white;
}

.edit-btn:hover {
  background-color: #ebb563;
}

.delete-btn {
  background-color: #f56c6c;
  color: white;
}

.delete-btn:hover {
  background-color: #f78989;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}

.loading-state {
  text-align: center;
  padding: 40px 0;
  color: #409eff;
  font-weight: 500;
}
</style>