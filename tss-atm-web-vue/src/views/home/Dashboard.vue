<div class="header-right">
  <el-button type="primary" @click="handleLogout">退出</el-button>
</div>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import * as authApi from '@/api/auth'

const router = useRouter()

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
</script> 