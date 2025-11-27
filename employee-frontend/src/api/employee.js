import axios from '../utils/axios'

export const getEmployeeList = (params) => {
  return axios.get('/api/employees', { params })
}

export const getEmployeeById = (id) => {
  return axios.get(`/api/employees/${id}`)
}

export const addEmployee = (data) => {
  return axios.post('/api/employees', data)
}

export const updateEmployee = (id, data) => {
  return axios.put(`/api/employees/${id}`, data)
}

export const deleteEmployee = (id) => {
  return axios.delete(`/api/employees/${id}`)
}

export const batchDeleteEmployee = (ids) => {
  return axios.delete(`/api/employees/batch`, { data: ids })
}