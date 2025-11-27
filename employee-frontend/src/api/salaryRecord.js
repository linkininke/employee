import axios from '../utils/axios'

export const getSalaryRecordList = () => {
  return axios.get('/api/salary-records')
}

export const getSalaryRecordById = (id) => {
  return axios.get(`/api/salary-records/${id}`)
}

export const getSalaryRecordsByEmployeeId = (employeeId) => {
  return axios.get(`/api/salary-records/employee/${employeeId}`)
}

export const addSalaryRecord = (data) => {
  return axios.post('/api/salary-records', data)
}

export const updateSalaryRecord = (id, data) => {
  return axios.put(`/api/salary-records/${id}`, data)
}

export const deleteSalaryRecord = (id) => {
  return axios.delete(`/api/salary-records/${id}`)
}