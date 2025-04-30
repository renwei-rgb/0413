<template>
  <div class="attendance-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>考勤打卡</span>
          <div class="current-time">{{ currentTime }}</div>
        </div>
      </template>

      <el-row :gutter="20" class="button-group">
        <el-col :span="12">
          <el-button
            type="primary"
            :disabled="checkInDisabled"
            @click="handleCheckIn"
            :loading="checkInLoading"
          >
            上班打卡
          </el-button>
        </el-col>
        <el-col :span="12">
          <el-button
            type="warning"
            :disabled="checkOutDisabled"
            @click="handleCheckOut"
            :loading="checkOutLoading"
          >
            下班打卡
          </el-button>
        </el-col>
      </el-row>

      <div class="attendance-info" v-if="todayRecord">
        <p>今日打卡记录：</p>
        <p>上班时间：{{ todayRecord.checkIn || '未打卡' }}</p>
        <p>下班时间：{{ todayRecord.checkOut || '未打卡' }}</p>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import dayjs from 'dayjs'

const currentTime = ref('')
const checkInLoading = ref(false)
const checkOutLoading = ref(false)
const checkInDisabled = ref(false)
const checkOutDisabled = ref(true)

const todayRecord = ref({
  checkIn: '',
  checkOut: ''
})

// 更新时间
const updateCurrentTime = () => {
  currentTime.value = dayjs().format('YYYY-MM-DD HH:mm:ss')
}

// 上班打卡
const handleCheckIn = async () => {
  const userId = localStorage.getItem('userId')
  if (!userId) {
    ElMessage.error('未获取到用户ID，请重新登录')
    return
  }
  try {
    checkInLoading.value = true
    const res = await axios.post('/api/attendance/check-in', null, {
      params: { userId }
    })
    if (res.data.code === 200 && res.data.data === true) {
      ElMessage.success('上班打卡成功')
      checkInDisabled.value = true
      checkOutDisabled.value = false
      todayRecord.value.checkIn = currentTime.value
    } else {
      ElMessage.error(res.data.message || '打卡失败')
    }
  } catch (error: any) {
    ElMessage.error(error.message || '打卡失败')
  } finally {
    checkInLoading.value = false
  }
}

// 下班打卡
const handleCheckOut = async () => {
  const userId = localStorage.getItem('userId')
  if (!userId) {
    ElMessage.error('未获取到用户ID，请重新登录')
    return
  }
  try {
    checkOutLoading.value = true
    const res = await axios.post('/api/attendance/check-out', null, {
      params: { userId }
    })
    if (res.data.code === 200 && res.data.data === true) {
      ElMessage.success('下班打卡成功')
      checkOutDisabled.value = true
      todayRecord.value.checkOut = currentTime.value
    } else {
      ElMessage.error(res.data.message || '打卡失败')
    }
  } catch (error: any) {
    ElMessage.error(error.message || '打卡失败')
  } finally {
    checkOutLoading.value = false
  }
}

onMounted(() => {
  updateCurrentTime()
  setInterval(updateCurrentTime, 1000)
  // TODO: 获取今日打卡记录
})
</script>

<style scoped>
.attendance-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.current-time {
  font-size: 20px;
  color: #409EFF;
}

.button-group {
  text-align: center;
  margin: 20px 0;
}

.el-button {
  width: 80%;
  height: 50px;
  font-size: 18px;
}

.attendance-info {
  margin-top: 20px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.attendance-info p {
  margin: 10px 0;
  font-size: 16px;
}
</style>
