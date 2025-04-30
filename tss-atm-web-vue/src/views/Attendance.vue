<template>
  <div class="attendance-container">
    <el-card class="attendance-card">
      <div class="card-header">
        <h2>上班打卡</h2>
      </div>
      <div class="card-body">
        <div class="current-time">当前时间：{{ currentTime }}</div>
        <el-button
          type="success"
          :disabled="checkedIn"
          @click="handleCheckIn"
          size="large"
        >
          {{ checkedIn ? '已打卡' : '上班打卡' }}
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const checkedIn = ref(false)
const currentTime = ref('')
const username = localStorage.getItem('username') || ''
const userId = localStorage.getItem('userId')

// 更新时间
let timer: number
const updateTime = () => {
  const now = new Date()
  const pad = (n: number) => n.toString().padStart(2, '0')
  currentTime.value = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  // 可选：页面加载时检查今天是否已打卡
  checkTodayStatus()
})

onUnmounted(() => {
  clearInterval(timer)
})

// 检查今天是否已打卡（可选，需后端支持）
const checkTodayStatus = async () => {
  try {
    const res = await axios.get('/api/attendance/checkin/status', {
      params: { username }
    })
    if (res.data.code === 200 && res.data.checkedIn) {
      checkedIn.value = true
    }
  } catch (e) {
    // 忽略
  }
}

const handleCheckIn = async () => {
  if (!userId) {
    ElMessage.error('未获取到用户ID，请重新登录')
    return
  }
  try {
    const res = await axios.post('/api/attendance/check-in', null, {
      params: { userId: userId }
    })
    if (res.data.code === 200) {
      checkedIn.value = true
      localStorage.setItem('token', res.data.data.token)
      localStorage.setItem('username', res.data.data.username)
      localStorage.setItem('userId', res.data.data.userId)
      ElMessage.success('打卡成功！')
    } else {
      ElMessage.error(res.data.message || '打卡失败')
    }
  } catch (e: any) {
    ElMessage.error(e.response?.data?.message || e.message || '打卡失败')
  }
}
</script>

<style scoped>
.attendance-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}
.attendance-card {
  width: 400px;
  padding: 32px 24px;
  box-shadow: 0 2px 12px rgba(64,158,255,0.08);
  border-radius: 12px;
  background: rgba(255,255,255,0.95);
}
.card-header {
  text-align: center;
  margin-bottom: 24px;
}
.current-time {
  font-size: 18px;
  margin-bottom: 24px;
  text-align: center;
}
</style>