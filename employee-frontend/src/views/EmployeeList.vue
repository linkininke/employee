<template>
  <div class="employee-list-container">
    <div class="page-header">
      <h1>员工列表</h1>
      <div class="header-actions">
        <button 
          class="batch-delete-btn" 
          @click="handleBatchDelete" 
          :disabled="selectedIds.length === 0"
        >
          批量删除
        </button>
        <button 
          class="import-btn" 
          @click="showImportDialog = true"
        >
          导入Excel
        </button>
        <button 
          class="export-btn" 
          @click="handleExport"
        >
          导出Excel
        </button>
        <router-link to="/employees/add" class="add-btn">+ 添加员工</router-link>
      </div>
    </div>
    
    <!-- 导入弹窗 -->
    <div v-if="showImportDialog" class="import-dialog-overlay" @click="showImportDialog = false">
      <div class="import-dialog" @click.stop>
        <div class="dialog-header">
          <h2>导入员工数据</h2>
          <button class="close-btn" @click="showImportDialog = false">×</button>
        </div>
        <div class="dialog-content">
          <div class="import-tips">
            <p>请选择要导入的Excel文件</p>
            <p class="tip-text">提示：请确保Excel文件格式与导出的模板一致</p>
          </div>
          <div class="file-upload">
            <input 
              type="file" 
              id="file-upload" 
              accept=".xlsx, .xls" 
              @change="handleFileChange"
              ref="fileInput"
            >
            <label for="file-upload" class="upload-btn">
              {{ selectedFile ? selectedFile.name : '选择文件' }}
            </label>
          </div>
        </div>
        <div class="dialog-footer">
          <button class="cancel-btn" @click="showImportDialog = false">取消</button>
          <button 
            class="confirm-btn" 
            @click="handleImport"
            :disabled="!selectedFile || importing"
          >
            {{ importing ? '导入中...' : '确认导入' }}
          </button>
        </div>
      </div>
    </div>
    
    <div class="search-box">
      <input type="text" v-model="employeeStore.searchQuery" placeholder="搜索员工姓名或部门" @input="handleSearch">
      <button class="search-btn">搜索</button>
    </div>
    
    <div class="table-container">
      <table class="employee-table">
        <thead>
          <tr>
            <th>
              <input type="checkbox" v-model="selectAll" @change="handleSelectAll">
            </th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>部门</th>
            <th>职位</th>
            <th>入职日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="employeeStore.loading">
            <td colspan="8" class="loading-state">加载中...</td>
          </tr>
          <tr v-else v-for="employee in employeeStore.employees" :key="employee.id">
            <td>
              <input 
                type="checkbox" 
                v-model="selectedIds" 
                :value="employee.id"
              >
            </td>
            <td>{{ employee.name }}</td>
            <td>{{ employee.gender }}</td>
            <td>{{ employee.age }}</td>
            <td>{{ getDepartmentName(employee.departmentId) }}</td>
            <td>{{ getPositionName(employee.positionId) }}</td>
            <td>{{ employee.hireDate }}</td>
            <td class="action-buttons">
              <router-link :to="`/employees/${employee.id}`" class="detail-btn">详情</router-link>
              <router-link :to="`/employees/edit/${employee.id}`" class="edit-btn">编辑</router-link>
              <button class="delete-btn" @click="handleDelete(employee.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-if="!employeeStore.loading && employeeStore.employees.length === 0" class="empty-state">
      <p>暂无员工数据</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useEmployeeStore } from '../store/employee'
import { getDepartmentList } from '../api/department'
import { getPositionList } from '../api/position'

const employeeStore = useEmployeeStore()
const selectedIds = ref([])
const departments = ref([])
const positions = ref([])
const showImportDialog = ref(false)
const selectedFile = ref(null)
const importing = ref(false)
const fileInput = ref(null)

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

// 全选功能
const selectAll = computed({
  get() {
    return selectedIds.value.length === employeeStore.employees.length && employeeStore.employees.length > 0
  },
  set(value) {
    if (value) {
      selectedIds.value = employeeStore.employees.map(employee => employee.id)
    } else {
      selectedIds.value = []
    }
  }
})

const handleSelectAll = () => {
  // 全选功能已通过 computed 属性实现
}

const handleSearch = () => {
  employeeStore.fetchEmployees()
  // 搜索后清空选择
  selectedIds.value = []
}

const handleDelete = async (id) => {
  if (confirm('确定要删除该员工吗？')) {
    try {
      await employeeStore.deleteEmployee(id)
      // 删除后从选择列表中移除
      selectedIds.value = selectedIds.value.filter(selectedId => selectedId !== id)
    } catch (error) {
      console.error('删除员工失败:', error)
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    alert('请先选择要删除的员工')
    return
  }
  
  if (confirm(`确定要删除选中的 ${selectedIds.value.length} 名员工吗？`)) {
    try {
      // 批量删除，逐个调用删除接口
      for (const id of selectedIds.value) {
        await employeeStore.deleteEmployee(id)
      }
      // 清空选择列表
      selectedIds.value = []
    } catch (error) {
      console.error('批量删除员工失败:', error)
    }
  }
}

// 导出员工数据为Excel
const handleExport = () => {
  // 直接调用后端导出API，浏览器会自动下载文件
  window.location.href = '/api/employees/export'
}

// 处理文件选择
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    selectedFile.value = file
  }
}

// 处理导入
const handleImport = async () => {
  if (!selectedFile.value) {
    alert('请选择要导入的文件')
    return
  }
  
  importing.value = true
  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)
    
    const response = await axios.post('/api/employees/import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    
    alert(response.data.msg)
    showImportDialog.value = false
    // 重新加载员工列表
    await employeeStore.fetchEmployees()
    // 清空文件选择
    selectedFile.value = null
    if (fileInput.value) {
      fileInput.value.value = ''
    }
  } catch (error) {
    console.error('导入失败:', error)
    alert('导入失败，请检查文件格式是否正确')
  } finally {
    importing.value = false
  }
}

onMounted(async () => {
  // 并行获取部门和职位列表
  await Promise.all([fetchDepartments(), fetchPositions()])
  // 获取员工列表
  employeeStore.fetchEmployees()
})
</script>

<style scoped>
.employee-list-container {
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

.import-btn {
  padding: 10px 16px;
  background-color: #e6a23c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.import-btn:hover {
  background-color: #ebb563;
}

.export-btn {
  padding: 10px 16px;
  background-color: #67c23a;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.export-btn:hover {
  background-color: #85ce61;
}

/* 导入弹窗样式 */
.import-dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.import-dialog {
  background-color: white;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.dialog-header h2 {
  margin: 0;
  color: #333;
  font-size: 18px;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #909399;
  transition: color 0.3s;
}

.close-btn:hover {
  color: #606266;
}

.dialog-content {
  padding: 20px;
}

.import-tips {
  margin-bottom: 20px;
}

.import-tips p {
  margin: 0 0 8px 0;
  color: #333;
}

.import-tips .tip-text {
  color: #909399;
  font-size: 14px;
}

.file-upload {
  margin-bottom: 20px;
}

.file-upload input[type="file"] {
  display: none;
}

.upload-btn {
  display: inline-block;
  padding: 10px 20px;
  background-color: #409eff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 14px;
}

.upload-btn:hover {
  background-color: #66b1ff;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
  border-top: 1px solid #eee;
}

.dialog-footer .cancel-btn {
  padding: 10px 20px;
  background-color: #909399;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 14px;
}

.dialog-footer .cancel-btn:hover {
  background-color: #a6a9ad;
}

.dialog-footer .confirm-btn {
  padding: 10px 20px;
  background-color: #67c23a;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 14px;
}

.dialog-footer .confirm-btn:hover:not(:disabled) {
  background-color: #85ce61;
}

.dialog-footer .confirm-btn:disabled {
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

.employee-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.employee-table th,
.employee-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.employee-table th {
  background-color: #f5f7fa;
  font-weight: 600;
  color: #333;
}

.employee-table tr:hover {
  background-color: #f5f7fa;
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.detail-btn,
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

.detail-btn {
  background-color: #909399;
  color: white;
}

.detail-btn:hover {
  background-color: #a6a9ad;
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