<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <h3>员工管理系统</h3>
      </div>
      <nav class="sidebar-nav">
        <router-link 
          v-for="menu in authStore.visibleMenus" 
          :key="menu.name" 
          :to="menu.path" 
          class="nav-item" 
          active-class="active"
        >
          <span class="nav-icon">{{ menu.icon }}</span>
          <span>{{ menu.label }}</span>
        </router-link>
      </nav>
    </aside>
    
    <!-- 主内容区 -->
    <main class="main-content">
      <!-- 顶部导航 -->
      <header class="top-nav">
        <div class="nav-left">
          <button class="toggle-btn" @click="toggleSidebar">☰</button>
        </div>
        <div class="nav-right">
          <div class="user-info">
            <span>{{ authStore.userInfo?.username || '未登录' }}</span>
            <button class="logout-btn" @click="handleLogout">退出登录</button>
          </div>
        </div>
      </header>
      
      <!-- 内容区域 -->
      <div class="content-wrapper">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const router = useRouter()
const authStore = useAuthStore()
const sidebarCollapsed = ref(false)

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

/* 侧边栏样式 */
.sidebar {
  width: 240px;
  background-color: #2c3e50;
  color: white;
  transition: width 0.3s;
  overflow: hidden;
}

.sidebar.collapsed {
  width: 60px;
}

.sidebar-header {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #34495e;
}

.sidebar-header h3 {
  margin: 0;
  font-size: 18px;
}

.sidebar-nav {
  padding: 20px 0;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  color: #ecf0f1;
  text-decoration: none;
  transition: background-color 0.3s;
}

.nav-item:hover {
  background-color: #34495e;
}

.nav-item.active {
  background-color: #3498db;
}

.nav-icon {
  font-size: 18px;
  margin-right: 12px;
}

/* 主内容区样式 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #ecf0f1;
  overflow: hidden;
}

/* 顶部导航样式 */
.top-nav {
  height: 60px;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.toggle-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.toggle-btn:hover {
  background-color: #f5f5f5;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.logout-btn {
  padding: 6px 12px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-btn:hover {
  background-color: #c0392b;
}

/* 内容包装器 */
.content-wrapper {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}
</style>