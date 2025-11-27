import axios from '../utils/axios'

export const login = (data) => {
  return axios.post('/auth/login', data)
}

export const getUserInfo = () => {
  return axios.get('/auth/me')
}