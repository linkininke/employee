import axios from '../utils/axios'

export const getDepartmentList = () => {
  return axios.get('/api/departments')
}

export const getDepartmentById = (id) => {
  return axios.get(`/api/departments/${id}`)
}

export const addDepartment = (data) => {
  return axios.post('/api/departments', data)
}

export const updateDepartment = (id, data) => {
  return axios.put(`/api/departments/${id}`, data)
}

export const deleteDepartment = (id) => {
  return axios.delete(`/api/departments/${id}`)
}