import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    // 用户信息
    userInfo: JSON.parse(localStorage.getItem('userInfo')) || null,
    // 登录状态
    isLoggedIn: !!localStorage.getItem('token'),
    // 菜单权限
    menus: [
      {
        path: '/dashboard',
        name: 'dashboard',
        label: '首页',
        icon: '📊',
        permission: 'dashboard:view'
      },
      {
        path: '/employees',
        name: 'employees',
        label: '员工列表',
        icon: '👥',
        permission: 'employee:list'
      },
      {
        path: '/employees/add',
        name: 'employee-add',
        label: '添加员工',
        icon: '➕',
        permission: 'employee:add',
        hidden: true
      },
      {
        path: '/employees/:id',
        name: 'employee-detail',
        label: '员工详情',
        icon: '👁️',
        permission: 'employee:view',
        hidden: true
      },
      {
        path: '/employees/edit/:id',
        name: 'employee-edit',
        label: '编辑员工',
        icon: '✏️',
        permission: 'employee:edit',
        hidden: true
      },
      {
        path: '/departments',
        name: 'departments',
        label: '部门管理',
        icon: '🏢',
        permission: 'department:list'
      },
      {
        path: '/departments/add',
        name: 'department-add',
        label: '添加部门',
        icon: '➕',
        permission: 'department:add',
        hidden: true
      },
      {
        path: '/departments/edit/:id',
        name: 'department-edit',
        label: '编辑部门',
        icon: '✏️',
        permission: 'department:edit',
        hidden: true
      },
      {
        path: '/positions',
        name: 'positions',
        label: '职位管理',
        icon: '👔',
        permission: 'position:list'
      },
      {
        path: '/positions/add',
        name: 'position-add',
        label: '添加职位',
        icon: '➕',
        permission: 'position:add',
        hidden: true
      },
      {
        path: '/positions/edit/:id',
        name: 'position-edit',
        label: '编辑职位',
        icon: '✏️',
        permission: 'position:edit',
        hidden: true
      },
      {
        path: '/salary-records',
        name: 'salary-records',
        label: '薪资记录',
        icon: '💰',
        permission: 'salary:list'
      },
      {
        path: '/salary-records/add',
        name: 'salary-record-add',
        label: '添加薪资记录',
        icon: '➕',
        permission: 'salary:add',
        hidden: true
      },
      {
        path: '/salary-records/edit/:id',
        name: 'salary-record-edit',
        label: '编辑薪资记录',
        icon: '✏️',
        permission: 'salary:edit',
        hidden: true
      }
    ]
  }),
  
  getters: {
    // 获取可见菜单
    visibleMenus: (state) => {
      return state.menus.filter(menu => !menu.hidden)
    },
    // 检查是否有权限
    hasPermission: (state) => (permission) => {
      // 这里可以根据实际的权限逻辑进行判断
      // 目前默认所有登录用户都有所有权限
      return state.isLoggedIn
    }
  },
  
  actions: {
    // 登录成功
    loginSuccess(userInfo, token) {
      this.userInfo = userInfo
      this.isLoggedIn = true
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
      localStorage.setItem('token', token)
    },
    // 退出登录
    logout() {
      this.userInfo = null
      this.isLoggedIn = false
      localStorage.removeItem('userInfo')
      localStorage.removeItem('token')
    },
    // 更新用户信息
    updateUserInfo(userInfo) {
      this.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    }
  }
})