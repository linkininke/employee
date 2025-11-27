import { defineStore } from 'pinia'
import { getEmployeeList, deleteEmployee, addEmployee, updateEmployee, getEmployeeById } from '../api/employee'

export const useEmployeeStore = defineStore('employee', {
  state: () => ({
    // 员工列表
    employees: [],
    // 当前员工详情
    currentEmployee: null,
    // 搜索条件
    searchQuery: '',
    // 加载状态
    loading: false,
    // 分页信息
    pagination: {
      page: 1,
      pageSize: 10,
      total: 0
    }
  }),
  
  getters: {
    // 过滤后的员工列表
    filteredEmployees: (state) => {
      if (!state.searchQuery) {
        return state.employees
      }
      const query = state.searchQuery.toLowerCase()
      return state.employees.filter(employee => 
        employee.name.toLowerCase().includes(query) || 
        employee.department.toLowerCase().includes(query)
      )
    }
  },
  
  actions: {
    // 获取员工列表
    async fetchEmployees(params = {}) {
      this.loading = true
      try {
        const res = await getEmployeeList({
          ...params,
          name: this.searchQuery
        })
        this.employees = res.data.records || []
        this.pagination.total = res.data.total || 0
      } catch (error) {
        console.error('获取员工列表失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    // 获取单个员工详情
    async fetchEmployeeById(id) {
      this.loading = true
      try {
        const res = await getEmployeeById(id)
        this.currentEmployee = res.data
        return res.data
      } catch (error) {
        console.error('获取员工详情失败:', error)
        throw error
      } finally {
        this.loading = false
      }
    },
    
    // 添加员工
    async addEmployee(data) {
      try {
        const res = await addEmployee(data)
        await this.fetchEmployees()
        return res
      } catch (error) {
        console.error('添加员工失败:', error)
        throw error
      }
    },
    
    // 更新员工
    async updateEmployee(id, data) {
      try {
        const res = await updateEmployee(id, data)
        await this.fetchEmployees()
        return res
      } catch (error) {
        console.error('更新员工失败:', error)
        throw error
      }
    },
    
    // 删除员工
    async deleteEmployee(id) {
      try {
        const res = await deleteEmployee(id)
        await this.fetchEmployees()
        return res
      } catch (error) {
        console.error('删除员工失败:', error)
        throw error
      }
    },
    
    // 设置搜索条件
    setSearchQuery(query) {
      this.searchQuery = query
      this.pagination.page = 1
    },
    
    // 清空当前员工详情
    clearCurrentEmployee() {
      this.currentEmployee = null
    },
    
    // 设置分页信息
    setPagination(page, pageSize) {
      this.pagination.page = page
      this.pagination.pageSize = pageSize
    }
  }
})