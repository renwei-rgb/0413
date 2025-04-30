<template>
  <div class="portal">
    <div class="header">
      <span>公司门户</span>
      <div class="right">
        <el-button v-if="!isLogin" @click="goLogin" type="primary" size="small">登录</el-button>
        <span v-else>欢迎，{{ username }}</span>
        <el-button v-if="isLogin" @click="logout" type="text" size="small" style="margin-left: 10px;">退出</el-button>
      </div>
    </div>
    <el-divider />
    <h2>公司公告</h2>
    <el-card v-for="notice in notices" :key="notice.id" class="notice-card">
      <div>{{ notice.title }}</div>
      <div style="color: #888; font-size: 12px;">{{ notice.date }}</div>
    </el-card>
    <el-divider />
    <h2>日本日历</h2>
    <!-- 这里可以用第三方日历组件或嵌入 iframe -->
    <iframe src="https://calendar.google.com/calendar/embed?src=ja.japanese%23holiday%40group.v.calendar.google.com&ctz=Asia%2FTokyo"
            style="border: 0" width="800" height="600" frameborder="0" scrolling="no"></iframe>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const notices = ref([
  { id: 1, title: '五一放假通知', date: '2024-04-25' },
  { id: 2, title: '公司搬迁公告', date: '2024-03-10' }
])

// 登录状态：token和username都存在才算登录
const isLogin = computed(() => {
  return !!localStorage.getItem('token') && !!localStorage.getItem('username')
})
const username = computed(() => localStorage.getItem('username') || '')

const goLogin = () => {
  router.push('/login')
}

const logout = () => {
  console.log('退出被点击') // 调试用
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  // 可选：清理其他登录信息
  router.push('/') // 退出后回到首页
  // 强制刷新，确保UI更新
  setTimeout(() => {
    window.location.reload()
  }, 100)
}
</script>

<style scoped>
.portal {
  min-height: 100vh;
  background: linear-gradient(135deg, #e3f0ff 0%, #b3d8ff 100%);
  padding: 40px 0;
}
.header {
  background: rgba(33, 150, 243, 0.95); /* 蓝色主色调 */
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(33,150,243,0.08);
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 900px;
  margin: 0 auto 24px auto;
  padding: 16px 32px;
  color: #fff;
}
.section-title {
  color: #2196f3;
  margin-top: 24px;
  margin-bottom: 12px;
  text-shadow: 1px 1px 2px #fff;
}
.notice-card {
  margin-bottom: 16px;
  box-shadow: 0 2px 12px rgba(33,150,243,0.08);
  border-radius: 8px;
  background: rgba(255,255,255,0.95);
  border-left: 6px solid #2196f3;
}
.notice-title {
  font-weight: bold;
  font-size: 16px;
  color: #1565c0;
}
.notice-date {
  color: #888;
  font-size: 12px;
  margin-top: 4px;
}
.right {
  display: flex;
  align-items: center;
}
.calendar-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 16px;
  background: rgba(255,255,255,0.85);
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(33,150,243,0.08);
  padding: 16px;
}
</style>
