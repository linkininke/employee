import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/',
      redirect: '/login' // 默认跳转到登录页面
    },
    {
      path: '/home',
      component: () => import('../components/Layout.vue'),
      children: [
        {
          path: '/dashboard',
          name: 'dashboard',
          component: () => import('../views/Dashboard.vue')
        },
        {
          path: '/employees',
          name: 'employees',
          component: () => import('../views/EmployeeList.vue')
        },
        {
          path: '/employees/:id',
          name: 'employee-detail',
          component: () => import('../views/EmployeeDetail.vue')
        },
        {
          path: '/employees/add',
          name: 'employee-add',
          component: () => import('../views/EmployeeAdd.vue')
        },
        {
          path: '/employees/edit/:id',
          name: 'employee-edit',
          component: () => import('../views/EmployeeEdit.vue')
        },
        {
          path: '/departments',
          name: 'departments',
          component: () => import('../views/DepartmentList.vue')
        },
        {
          path: '/departments/add',
          name: 'department-add',
          component: () => import('../views/DepartmentAdd.vue')
        },
        {
          path: '/departments/edit/:id',
          name: 'department-edit',
          component: () => import('../views/DepartmentEdit.vue')
        },
        {
          path: '/positions',
          name: 'positions',
          component: () => import('../views/PositionList.vue')
        },
        {
          path: '/positions/add',
          name: 'position-add',
          component: () => import('../views/PositionAdd.vue')
        },
        {
          path: '/positions/edit/:id',
          name: 'position-edit',
          component: () => import('../views/PositionEdit.vue')
        },
        {
          path: '/salary-records',
          name: 'salary-records',
          component: () => import('../views/SalaryRecordList.vue')
        },
        {
          path: '/salary-records/add',
          name: 'salary-record-add',
          component: () => import('../views/SalaryRecordAdd.vue')
        },
        {
          path: '/salary-records/edit/:id',
          name: 'salary-record-edit',
          component: () => import('../views/SalaryRecordEdit.vue')
        }
      ]
    }
  ]
})

export default router