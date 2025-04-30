<template>
  <div class="dashboard-container">
    <h1>考勤看板</h1>
    <div>欢迎，{{ username }}</div>
    <el-row :gutter="20">
      <!-- 今日考勤状态 -->
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>今日考勤状态</span>
            </div>
          </template>
          <div class="attendance-status">
            <el-tag :type="attendanceStatus.type">{{ attendanceStatus.text }}</el-tag>
          </div>
        </el-card>
      </el-col>

      <!-- 本月统计 -->
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>本月出勤</span>
            </div>
          </template>
          <div class="stat-value">{{ monthlyStats.attendanceDays }} 天</div>
        </el-card>
      </el-col>

      <!-- 加班时长 -->
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>本月加班</span>
            </div>
          </template>
          <div class="stat-value">{{ monthlyStats.overtimeHours }} 小时</div>
        </el-card>
      </el-col>

      <!-- 请假天数 -->
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>本月请假</span>
            </div>
          </template>
          <div class="stat-value">{{ monthlyStats.leaveDays }} 天</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近打卡记录 -->
    <el-card style="margin-top: 20px;">
      <template #header>
        <div class="card-header">
          <span>最近打卡记录</span>
        </div>
      </template>
      <el-table :data="recentRecords" style="width: 100%">
        <el-table-column prop="date" label="日期" width="180" />
        <el-table-column prop="checkIn" label="上班时间" width="180" />
        <el-table-column prop="checkOut" label="下班时间" width="180" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === '正常' ? 'success' : 'warning'">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

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
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'

// 考勤状态
const attendanceStatus = ref({
  type: 'success',
  text: '已打卡'
})

// 月度统计
const monthlyStats = ref({
  attendanceDays: 15,
  overtimeHours: 5,
  leaveDays: 1
})

// 最近打卡记录
const recentRecords = ref([
  {
    date: '2024-03-20',
    checkIn: '09:00:00',
    checkOut: '18:00:00',
    status: '正常'
  },
  {
    date: '2024-03-19',
    checkIn: '09:15:00',
    checkOut: '18:30:00',
    status: '迟到'
  }
])

const username = computed(() => localStorage.getItem('username') || '')

const checkInLoading = ref(false)
const checkOutLoading = ref(false)
const checkInDisabled = ref(false)
const checkOutDisabled = ref(true)

const handleCheckIn = async () => {
  // Implementation of handleCheckIn
}

const handleCheckOut = async () => {
  // Implementation of handleCheckOut
}

onMounted(() => {
  // 这里可以调用API获取实际数据
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.attendance-status {
  text-align: center;
  font-size: 16px;
}

.stat-value {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.button-group {
  margin-top: 20px;
}
</style>
