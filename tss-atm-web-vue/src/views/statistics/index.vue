<template>
  <div class="statistics-container">
    <!-- 日期范围选择 -->
    <el-card class="filter-card">
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="统计日期">
          <el-date-picker
            v-model="queryForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :shortcuts="dateShortcuts"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery" :loading="loading">
            查询
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>出勤天数</span>
            </div>
          </template>
          <div class="stat-value">{{ statistics.attendanceDays }} 天</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>正常出勤</span>
            </div>
          </template>
          <div class="stat-value">{{ statistics.normalDays }} 天</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>迟到次数</span>
            </div>
          </template>
          <div class="stat-value">{{ statistics.lateDays }} 次</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>早退次数</span>
            </div>
          </template>
          <div class="stat-value">{{ statistics.earlyDays }} 次</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 考勤记录表格 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>考勤记录</span>
          <el-button type="primary" @click="handleExport" :loading="exporting">
            导出Excel
          </el-button>
        </div>
      </template>
      
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="date" label="日期" width="120" />
        <el-table-column prop="weekday" label="星期" width="100" />
        <el-table-column prop="checkIn" label="上班时间" width="180" />
        <el-table-column prop="checkOut" label="下班时间" width="180" />
        <el-table-column prop="workHours" label="工作时长" width="120" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="queryForm.page"
          v-model:page-size="queryForm.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'

// 查询表单
const queryForm = reactive({
  dateRange: [] as string[],
  page: 1,
  pageSize: 10
})

// 日期快捷选项
const dateShortcuts = [
  {
    text: '最近一周',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    }
  },
  {
    text: '最近一月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    }
  },
  {
    text: '本月',
    value: () => {
      const now = new Date()
      const start = new Date(now.getFullYear(), now.getMonth(), 1)
      const end = new Date(now.getFullYear(), now.getMonth() + 1, 0)
      return [start, end]
    }
  }
]

// 统计数据
const statistics = reactive({
  attendanceDays: 0,
  normalDays: 0,
  lateDays: 0,
  earlyDays: 0
})

// 表格数据
const tableData = ref([])
const total = ref(0)
const loading = ref(false)
const exporting = ref(false)

// 获取状态标签类型
const getStatusType = (status: string) => {
  switch (status) {
    case '正常':
      return 'success'
    case '迟到':
      return 'warning'
    case '早退':
      return 'danger'
    default:
      return 'info'
  }
}

// 查询数据
const handleQuery = async () => {
  if (!queryForm.dateRange || queryForm.dateRange.length !== 2) {
    ElMessage.warning('请选择日期范围')
    return
  }

  loading.value = true
  try {
    // TODO: 调用考勤统计API
    // const res = await getAttendanceStatistics({
    //   startDate: queryForm.dateRange[0],
    //   endDate: queryForm.dateRange[1],
    //   page: queryForm.page,
    //   pageSize: queryForm.pageSize
    // })

    // 模拟数据
    await new Promise(resolve => setTimeout(resolve, 1000))
    statistics.attendanceDays = 22
    statistics.normalDays = 20
    statistics.lateDays = 1
    statistics.earlyDays = 1

    tableData.value = [
      {
        date: '2024-03-20',
        weekday: '星期三',
        checkIn: '09:00:00',
        checkOut: '18:00:00',
        workHours: '9.0',
        status: '正常',
        remark: ''
      },
      {
        date: '2024-03-19',
        weekday: '星期二',
        checkIn: '09:15:00',
        checkOut: '18:00:00',
        workHours: '8.75',
        status: '迟到',
        remark: '迟到15分钟'
      }
    ]
    total.value = 22
  } catch (error: any) {
    ElMessage.error(error.message || '查询失败')
  } finally {
    loading.value = false
  }
}

// 导出Excel
const handleExport = async () => {
  if (!queryForm.dateRange || queryForm.dateRange.length !== 2) {
    ElMessage.warning('请选择日期范围')
    return
  }

  exporting.value = true
  try {
    // TODO: 调用导出API
    await new Promise(resolve => setTimeout(resolve, 1000))
    ElMessage.success('导出成功')
  } catch (error: any) {
    ElMessage.error(error.message || '导出失败')
  } finally {
    exporting.value = false
  }
}

// 处理分页
const handleSizeChange = (val: number) => {
  queryForm.pageSize = val
  handleQuery()
}

const handleCurrentChange = (val: number) => {
  queryForm.page = val
  handleQuery()
}
</script>

<style scoped>
.statistics-container {
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.stat-cards {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-value {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.table-card {
  margin-top: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
