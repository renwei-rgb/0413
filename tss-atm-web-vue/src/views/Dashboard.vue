<template>
  <div class="dashboard-container">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical"
          @select="handleSelect"
        >
          <el-menu-item index="overview">
            <el-icon><DataLine /></el-icon>
            <span>Overview</span>
          </el-menu-item>
          <el-menu-item index="transactions">
            <el-icon><Money /></el-icon>
            <span>Transactions</span>
          </el-menu-item>
          <el-menu-item index="profile">
            <el-icon><User /></el-icon>
            <span>Profile</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-container>
        <el-header>
          <div class="header-content">
            <h2>ATM System</h2>
            <el-dropdown @command="handleCommand">
              <span class="user-dropdown">
                {{ username }}
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">Profile</el-dropdown-item>
                  <el-dropdown-item command="logout">Logout</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { DataLine, Money, User, ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()
const activeMenu = ref('overview')
const username = ref(localStorage.getItem('username') || 'User')

const handleSelect = (key: string) => {
  router.push(`/dashboard/${key}`)
}

const handleCommand = (command: string) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    ElMessage.success('Logged out successfully')
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/dashboard/profile')
  }
}
</script>

<style scoped>
.dashboard-container {
  height: 100vh;
}

.el-container {
  height: 100%;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.el-menu {
  border-right: none;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  padding: 0 20px;
}

.header-content {
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-dropdown {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 