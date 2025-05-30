<template>
  <div class="dashboard-container">
    <div class="header">
      <h2>工作台</h2>
      <div class="header-right">
        <el-button type="primary" @click="handleLogout">退出</el-button>
      </div>
    </div>
    <div class="content">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>今日打卡</span>
              </div>
            </template>
            <div class="card-content">
              <p>上班时间：{{ todayRecord.checkIn || '未打卡' }}</p>
              <p>下班时间：{{ todayRecord.checkOut || '未打卡' }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>本月统计</span>
              </div>
            </template>
            <div class="card-content">
              <p>出勤天数：{{ monthlyStats.workDays || 0 }}</p>
              <p>迟到次数：{{ monthlyStats.lateDays || 0 }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <template #header>
              <div class="card-header">
                <span>个人信息</span>
              </div>
            </template>
            <div class="card-content">
              <p>姓名：{{ userInfo.realName || '未设置' }}</p>
              <p>部门：{{ userInfo.department || '未设置' }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import * as authApi from '@/api/auth'

const router = useRouter()

// 用户信息
const userInfo = ref({
  realName: '',
  department: ''
})

// 今日打卡记录
const todayRecord = ref({
  checkIn: '',
  checkOut: ''
})

// 本月统计
const monthlyStats = ref({
  workDays: 0,
  lateDays: 0
})

// 退出登录
const handleLogout = async () => {
  try {
    await authApi.logout()
    ElMessage.success('退出成功')
    router.push('/login')
  } catch (error) {
    console.error('退出失败:', error)
    ElMessage.error('退出失败')
  }
}

// 获取用户信息
const getUserInfo = async () => {
  try {
    // TODO: 调用获取用户信息API
    userInfo.value = {
      realName: '张三',
      department: '技术部'
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

// 获取今日打卡记录
const getTodayRecord = async () => {
  try {
    // TODO: 调用获取今日打卡记录API
    todayRecord.value = {
      checkIn: '09:00',
      checkOut: '18:00'
    }
  } catch (error) {
    console.error('获取今日打卡记录失败:', error)
  }
}

// 获取本月统计
const getMonthlyStats = async () => {
  try {
    // TODO: 调用获取本月统计API
    monthlyStats.value = {
      workDays: 20,
      lateDays: 1
    }
  } catch (error) {
    console.error('获取本月统计失败:', error)
  }
}

onMounted(() => {
  getUserInfo()
  getTodayRecord()
  getMonthlyStats()
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h2 {
  margin: 0;
  color: #303133;
}

.content {
  margin-top: 20px;
}

.box-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  color: #606266;
}

.card-content p {
  margin: 10px 0;
}
</style> 