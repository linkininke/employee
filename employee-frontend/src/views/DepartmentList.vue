<template>
  <div class="department-list-container">
    <div class="page-header">
      <h1>部门列表</h1>
      <div class="header-actions">
        <button 
          class="batch-delete-btn" 
          @click="handleBatchDelete" 
          :disabled="selectedIds.length === 0"
        >
          批量删除
        </button>
        <router-link to="/departments/add" class="add-btn">+ 添加部门</router-link>
      </div>
    </div>
    
    <div class="search-box">
      <input type="text" v-model="searchQuery" placeholder="搜索部门名称" @input="handleSearch">
      <button class="search-btn">搜索</button>
    </div>
    
    <div class="table-container">
      <table class="department-table">
        <thead>
          <tr>
            <th>
              <input type="checkbox" v-model="selectAll" @change="handleSelectAll">
            </th>
            <th>部门名称</th>
            <th>部门描述</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="5" class="loading-state">加载中...</td>
          </tr>
          <tr v-else v-for="department in departments" :key="department.id">
            <td>
              <input 
                type="checkbox" 
                v-model="selectedIds" 
                :value="department.id"
              >
            </td>
            <td>{{ department.name }}</td>
            <td>{{ department.description || '-' }}</td>
            <td>{{ formatDate(department.createTime) }}</td>
            <td class="action-buttons">
              <router-link :to="`/departments/edit/${department.id}`" class="edit-btn">编辑</router-link>
              <button class="delete-btn" @click="handleDelete(department.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="!loading && departments.length === 0" class="empty-state">
      <p>暂无部门数据</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { getDepartmentList, deleteDepartment } from '../api/department'

const departments = ref([])
const loading = ref(false)
const searchQuery = ref('')
const selectedIds = ref([])

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString()
}

// 加载部门列表
const fetchDepartments = async () => {
  loading.value = true
  try {
    const response = await getDepartmentList()
    departments.value = response.data
  } catch (error) {
    console.error('获取部门列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 全选功能
const selectAll = computed({
  get() {
    return selectedIds.value.length === departments.value.length && departments.value.length > 0
  },
  set(value) {
    if (value) {
      selectedIds.value = departments.value.map(department => department.id)
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
  fetchDepartments()
  // 搜索后清空选择
  selectedIds.value = []
}

const handleDelete = async (id) => {
  if (confirm('确定要删除该部门吗？')) {
    try {
      await deleteDepartment(id)
      // 删除后重新加载列表
      await fetchDepartments()
      // 删除后从选择列表中移除
      selectedIds.value = selectedIds.value.filter(selectedId => selectedId !== id)
    } catch (error) {
      console.error('删除部门失败:', error)
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    alert('请先选择要删除的部门')
    return
  }
  
  if (confirm(`确定要删除选中的 ${selectedIds.value.length} 个部门吗？`)) {
    try {
      // 批量删除，逐个调用删除接口
      for (const id of selectedIds.value) {
        await deleteDepartment(id)
      }
      // 重新加载列表
      await fetchDepartments()
      // 清空选择列表
      selectedIds.value = []
    } catch (error) {
      console.error('批量删除部门失败:', error)
    }
  }
}

onMounted(() => {
  fetchDepartments()
})
</script>

<style scoped>
.department-list-container {
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

.department-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.department-table th,
.department-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.department-table th {
  background-color: #f5f7fa;
  font-weight: 600;
  color: #333;
}

.department-table tr:hover {
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