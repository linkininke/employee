import axios from '../utils/axios'

export const getPositionList = () => {
  return axios.get('/api/positions')
}

export const getPositionById = (id) => {
  return axios.get(`/api/positions/${id}`)
}

export const addPosition = (data) => {
  return axios.post('/api/positions', data)
}

export const updatePosition = (id, data) => {
  return axios.put(`/api/positions/${id}`, data)
}

export const deletePosition = (id) => {
  return axios.delete(`/api/positions/${id}`)
}