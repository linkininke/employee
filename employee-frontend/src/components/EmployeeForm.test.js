import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import EmployeeForm from './EmployeeForm.vue'
import { getDepartmentList } from '../api/department'
import { getPositionList } from '../api/position'

// 模拟API调用
vi.mock('../api/department', () => ({
  getDepartmentList: vi.fn()
}))

vi.mock('../api/position', () => ({
  getPositionList: vi.fn()
}))

describe('EmployeeForm Component', () => {
  // 模拟部门和职位数据
  const mockDepartments = [
    { id: 1, name: '技术部' },
    { id: 2, name: '人事部' }
  ]

  const mockPositions = [
    { id: 1, name: '前端开发工程师' },
    { id: 2, name: '后端开发工程师' }
  ]

  beforeEach(() => {
    // 重置所有模拟
    vi.clearAllMocks()
    
    // 设置默认的模拟返回值
    getDepartmentList.mockResolvedValue({ data: mockDepartments })
    getPositionList.mockResolvedValue({ data: mockPositions })
  })

  it('should render the form correctly', async () => {
    const wrapper = mount(EmployeeForm)
    
    // 等待异步数据加载
    await wrapper.vm.$nextTick()
    
    // 检查表单元素是否存在
    expect(wrapper.find('form').exists()).toBe(true)
    expect(wrapper.find('#name').exists()).toBe(true)
    expect(wrapper.find('#gender').exists()).toBe(true)
    expect(wrapper.find('#age').exists()).toBe(true)
    expect(wrapper.find('#departmentId').exists()).toBe(true)
    expect(wrapper.find('#positionId').exists()).toBe(true)
    expect(wrapper.find('#salary').exists()).toBe(true)
    expect(wrapper.find('#hireDate').exists()).toBe(true)
    expect(wrapper.find('.submit-btn').exists()).toBe(true)
    expect(wrapper.find('.cancel-btn').exists()).toBe(true)
  })

  it('should have empty form fields initially', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 检查表单初始值是否为空
    expect(wrapper.vm.form.name).toBe('')
    expect(wrapper.vm.form.gender).toBe('')
    expect(wrapper.vm.form.age).toBe('')
    expect(wrapper.vm.form.departmentId).toBe('')
    expect(wrapper.vm.form.positionId).toBe('')
    expect(wrapper.vm.form.salary).toBe('')
    expect(wrapper.vm.form.hireDate).toBe('')
  })

  it('should fill form with employee data when prop is provided', async () => {
    const mockEmployee = {
      id: 1,
      name: '张三',
      gender: '男',
      age: 25,
      departmentId: 1,
      positionId: 1,
      salary: 5000,
      hireDate: '2023-01-01'
    }
    
    const wrapper = mount(EmployeeForm, {
      props: {
        employee: mockEmployee
      }
    })
    
    await wrapper.vm.$nextTick()
    
    // 检查表单是否填充了员工数据
    expect(wrapper.vm.form.name).toBe('张三')
    expect(wrapper.vm.form.gender).toBe('男')
    expect(wrapper.vm.form.age).toBe(25)
    expect(wrapper.vm.form.departmentId).toBe(1)
    expect(wrapper.vm.form.positionId).toBe(1)
    expect(wrapper.vm.form.salary).toBe(5000)
    expect(wrapper.vm.form.hireDate).toBe('2023-01-01')
    
    // 检查提交按钮文本是否为"保存修改"
    expect(wrapper.find('.submit-btn').text()).toBe('保存修改')
  })

  it('should emit submit event with form data when form is submitted', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 填充表单数据
    await wrapper.find('#name').setValue('李四')
    await wrapper.find('#gender').setValue('女')
    await wrapper.find('#age').setValue(30)
    await wrapper.find('#departmentId').setValue(2)
    await wrapper.find('#positionId').setValue(2)
    await wrapper.find('#salary').setValue(6000)
    await wrapper.find('#hireDate').setValue('2023-02-01')
    
    // 提交表单
    await wrapper.find('form').trigger('submit.prevent')
    
    // 检查是否触发了submit事件，并且传递了正确的表单数据
    expect(wrapper.emitted('submit')).toHaveLength(1)
    expect(wrapper.emitted('submit')[0][0]).toEqual({
      name: '李四',
      gender: '女',
      age: 30,
      departmentId: 2,
      positionId: 2,
      salary: 6000,
      hireDate: '2023-02-01'
    })
  })

  it('should emit cancel event when cancel button is clicked', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 点击取消按钮
    await wrapper.find('.cancel-btn').trigger('click')
    
    // 检查是否触发了cancel事件
    expect(wrapper.emitted('cancel')).toHaveLength(1)
  })

  it('should display "添加员工" as submit button text when in add mode', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 检查提交按钮文本是否为"添加员工"
    expect(wrapper.find('.submit-btn').text()).toBe('添加员工')
  })

  it('should load departments and positions on mount', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 检查API是否被调用
    expect(getDepartmentList).toHaveBeenCalledTimes(1)
    expect(getPositionList).toHaveBeenCalledTimes(1)
  })

  it('should update form when employee prop changes', async () => {
    const wrapper = mount(EmployeeForm)
    
    await wrapper.vm.$nextTick()
    
    // 初始状态应该是添加模式
    expect(wrapper.vm.isEdit).toBe(false)
    
    // 更新employee prop
    const mockEmployee = {
      id: 1,
      name: '王五',
      gender: '男',
      age: 28,
      departmentId: 1,
      positionId: 1,
      salary: 5500,
      hireDate: '2023-03-01'
    }
    
    await wrapper.setProps({ employee: mockEmployee })
    await wrapper.vm.$nextTick()
    
    // 检查表单是否更新
    expect(wrapper.vm.isEdit).toBe(true)
    expect(wrapper.vm.form.name).toBe('王五')
    
    // 移除employee prop，应该重置表单
    await wrapper.setProps({ employee: null })
    await wrapper.vm.$nextTick()
    
    expect(wrapper.vm.isEdit).toBe(false)
    expect(wrapper.vm.form.name).toBe('')
  })
})
