import axios from 'axios'
import router from '../router'

const instance = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      const { status } = error.response
      switch (status) {
        case 401:
          // token过期或无效，清除本地token并跳转到登录页
          localStorage.removeItem('token')
          localStorage.removeItem('userInfo')
          router.push('/login')
          break
        case 403:
          alert('没有权限访问该资源')
          break
        case 500:
          alert('服务器内部错误')
          break
        default:
          alert(`请求失败：${error.response.data.message || '未知错误'}`)
      }
    } else if (error.request) {
      alert('网络错误，请检查网络连接')
    } else {
      alert(`请求失败：${error.message}`)
    }
    return Promise.reject(error)
  }
)

export default instance