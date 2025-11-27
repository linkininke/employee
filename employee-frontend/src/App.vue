<script setup>
// App.vue 入口组件，使用路由视图
</script>

<template>
  <!-- 路由视图，根据当前路由显示对应的组件 -->
  <router-view />
</template>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  background-color: #f5f7fa;
  color: #333;
}

/* 全局链接样式 */
a {
  text-decoration: none;
  color: #409eff;
}

/* 全局按钮样式 */
button {
  cursor: pointer;
}
</style>
