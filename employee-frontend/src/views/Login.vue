<template>
  <div class="login-container">
    <div class="login-box">
      <h1>员工信息管理系统</h1>
      <h2>登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-item">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="form.username" placeholder="请输入用户名" required>
        </div>
        <div class="form-item">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="form.password" placeholder="请输入密码" required>
        </div>
        <button type="submit" class="login-btn">登录</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useAuthStore } from '../store/auth'

const router = useRouter()
const authStore = useAuthStore()
const form = ref({
  username: '',
  password: ''
})

const handleLogin = async () => {
  try {
    console.log('登录请求数据:', form.value)
    
    // 直接使用axios发送请求，不使用封装的api
    const res = await axios.post('http://localhost:8080/api/auth/login', form.value, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    
    console.log('登录响应数据:', res)
    console.log('登录响应状态:', res.status)
    console.log('登录响应数据:', res.data)
    
    // 后端返回的格式是 {token: 'xxx', username: 'admin'}，没有user字段
    authStore.loginSuccess({ username: res.data.username }, res.data.token)
    router.push('/dashboard')
  } catch (error) {
    console.error('登录失败:', error)
    if (error.response) {
      console.error('错误状态:', error.response.status)
      console.error('错误数据:', error.response.data)
      console.error('错误头:', error.response.headers)
    } else if (error.request) {
      console.error('请求发送失败:', error.request)
    } else {
      console.error('请求配置错误:', error.message)
    }
    alert('登录失败，请检查用户名和密码')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 400px;
  padding: 30px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.login-box h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.login-box h2 {
  text-align: center;
  color: #666;
  margin-bottom: 30px;
  font-size: 18px;
}

.form-item {
  margin-bottom: 20px;
}

.form-item label {
  display: block;
  margin-bottom: 8px;
  color: #555;
  font-weight: 500;
}

.form-item input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s;
}

.form-item input:focus {
  outline: none;
  border-color: #409eff;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #66b1ff;
}
</style>