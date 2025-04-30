<template>
  <el-container class="layout-container">
    <el-header>
      <div class="header-content">
        <div class="logo">考勤管理系统</div>
        <div class="user-info">
          <el-dropdown>
            <span class="user-dropdown">
              {{ username }}
              <el-icon><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-menu
            :default-active="activeMenu"
            class="menu"
            router
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

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { Menu, Calendar, DataLine, ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const username = ref('Admin') // 这里应该从用户状态获取
const activeMenu = ref('/home/dashboard')

const handleLogout = () => {
  ElMessageBox.confirm('确认退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('token')
    router.push('/login')
  })
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  background-color: #409EFF;
  color: white;
  padding: 0;
}

.header-content {
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

.user-dropdown {
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

.menu {
  height: 100%;
  border-right: none;
}

.el-main {
  background-color: #f5f7fa;
  padding: 20px;
}
</style>