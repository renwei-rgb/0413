<template>
  <el-container class="layout-container">
    <!-- 头部 -->
    <el-header class="header">
      <div class="header-left">
        <h2 class="logo">tss考勤系统1.0</h2>
      </div>
      <div class="header-right">
        <el-button v-if="!isLogin" @click="goLogin">登录</el-button>
        <span v-else>欢迎，{{ username }}</span>
        <el-button v-if="isLogin" @click="handleCommand('logout')" type="text" size="small" style="margin-left: 10px;">退出</el-button>
      </div>
    </el-header>

    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical"
          :router="true"
          :unique-opened="true"
        >
          <el-menu-item index="/home/dashboard">
            <el-icon><menu /></el-icon>
            <span>考勤看板</span>
          </el-menu-item>
          <el-menu-item index="/home/attendance">
            <el-icon><calendar /></el-icon>
            <span>考勤打卡</span>
          </el-menu-item>
          <el-menu-item index="/home/statistics">
            <el-icon><data-line /></el-icon>
            <span>考勤统计</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主要内容区 -->
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { Menu, Calendar, DataLine, ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()

// 当前激活的菜单项
const activeMenu = computed(() => route.path)

// 用户名和登录状态用 computed，实时读取 localStorage
const username = computed(() => localStorage.getItem('username') || '')
const isLogin = computed(() => !!localStorage.getItem('token') && !!localStorage.getItem('username'))

const handleCommand = (command: string) => {
  switch (command) {
    case 'profile':
      // 跳转到个人信息页面
      break
    case 'logout':
      ElMessageBox.confirm('确认退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除登录信息
        localStorage.removeItem('token')
        localStorage.removeItem('username')
        // 跳转到登录页
        router.push('/login')
      })
      break
  }
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  background-color: #409EFF;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.logo {
  margin: 0;
  font-size: 20px;
}

.user-info {
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 4px;
}

.el-aside {
  background-color: #fff;
  border-right: solid 1px #e6e6e6;
}

.el-menu-vertical {
  border-right: none;
}

.el-main {
  background-color: #f5f7fa;
  padding: 20px;
}
</style>
